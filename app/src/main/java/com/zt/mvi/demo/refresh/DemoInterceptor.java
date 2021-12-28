package com.zt.mvi.demo.refresh;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.lib.net.L;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;

public class DemoInterceptor implements Interceptor {

    private HashMap<String, Object> headers;
    private HashMap<String, Object> queries;

    public DemoInterceptor() {
        headers = new HashMap();
        queries = new HashMap();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();
        L.i("uudd","============"+request.method() +"   "+url);
        //http://192.168.0.28:32284/api/n/user/isValid

        if(url.indexOf("/api/n/server/time") == -1
                && url.indexOf("/api/f/upload") == -1
                && url.indexOf("/api/n/user/isValid") == -1
                && url.indexOf("http://pv.sohu.com/cityjson?ie=utf-8") == -1) {

            Request.Builder requestBuilder = request.newBuilder();
            HttpUrl.Builder urlBuilder = request.url().newBuilder();
            String ts = (System.currentTimeMillis()+ 0)+"";

            if ("GET".equals(request.method())) { // GET方法
                // 这里可以添加一些公共get参数
                urlBuilder.removeAllEncodedQueryParameters("ts");
                urlBuilder.addEncodedQueryParameter("ts", ts);
                HttpUrl oldHttpUrl = urlBuilder.build();
                Set<String> paramKeys = oldHttpUrl.queryParameterNames();

                JSONObject params = new JSONObject();
                HttpUrl.Builder builder = null;
                for (String key : paramKeys) {
                    String value = oldHttpUrl.queryParameter(key);
                    if(builder == null) builder = oldHttpUrl.newBuilder();
                    //builder.addEncodedQueryParameter(key, value);
                    params.put(key, value);
                    //L.i("uudd", key + "    (*)    " + value);
                }

                builder.removeAllEncodedQueryParameters("sign");
                builder.addEncodedQueryParameter("sign", SecureUtil.createSign(params.toString()));
                //L.i("uudd",  "sign    (*)    " + SecureUtil.createSign(params.toString()));

                HttpUrl newHttpUrl = builder.build();
                request = requestBuilder.url(newHttpUrl).build();
                    /*Set<String> paramKeys2 = newHttpUrl.queryParameterNames();
                    for (String key : paramKeys2) {
                        String value = newHttpUrl.queryParameter(key);
                        L.i("uudd", key + "    ($)    " + value);
                    }*/

            } else if ("POST".equals(request.method())) { // POST方法
                RequestBody body = request.body();
                Buffer buffer = new Buffer();
                body.writeTo(buffer);

                Charset UTF8 = Charset.forName("UTF-8");
                Charset charset = UTF8;
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                final String newBody = buffer.readString(charset);
                L.i("uudd","intercept-------------newBody:" + newBody);
                JSONObject postBody = null;
                try {
                    postBody = JSON.parseObject(newBody);
                    postBody.put("ts", ts);
                    postBody.remove("sign");
                    postBody.put("sign", SecureUtil.createSign(postBody.toString()));
                }catch (Exception e) {
                }

                if(postBody == null) {
                    postBody = new JSONObject();
                    postBody.put("ts", ts);
                    postBody.remove("sign");
                    postBody.put("sign", SecureUtil.createSign(postBody.toString()));
                }


                L.i("uudd","intercept-------------postBody:" + postBody);
                JSONObject finalPostBody = postBody;
                RequestBody requestBody = new RequestBody() {
                    @Override
                    public void writeTo(@NonNull BufferedSink sink) throws IOException {
                        sink.writeString(finalPostBody.toString(), Charset.forName("utf-8"));
                    }

                    @Override public MediaType contentType() {
                        return MediaType.get("application/json; charset=utf-8");
                    }
                };
                request = request.newBuilder().post(requestBody).build();
            }

        }else {
            L.i("uudd", "---------------------不拦截哦 哦哦哦哦哦");
        }

        return chain.proceed(request);
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    public DemoInterceptor addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public DemoInterceptor addQuery(String name, String value) {
        queries.put(name, value);
        return this;
    }

    public HashMap<String, Object> getHeaders() {
        return headers;
    }

    public HashMap<String, Object> getQuerys() {
        return queries;
    }

}