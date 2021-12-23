package com.base.lib.net.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BaseInterceptor implements Interceptor {

    private HashMap<String, String> headers;
    private HashMap<String, String> queries;

    public BaseInterceptor() {
        headers = new HashMap();
        queries = new HashMap();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();

        HashMap<String, String> headerMap = headers;
        if (headerMap.size() > 0) {
            Headers headers = request.headers();
            Set<String> names = headers.names();
            Iterator<String> iterator = names.iterator();
            Request.Builder requestBuilder = request.newBuilder();
            for (String key : headerMap.keySet()) {
                String value = headerMap.get(key);
                requestBuilder.addHeader(key, value);
            }
            while (iterator.hasNext()) {
                String name = iterator.next();
                requestBuilder.addHeader(name, headers.get(name));
            }
            requestBuilder.addHeader("Connection","close");
            request = requestBuilder.build();
        }
        ////L.i("===========headers============"+headers.toString());

        HashMap<String, String> queryMap = queries;
        if (queryMap.size() > 0) {
            HttpUrl.Builder httpUrlBuilder = request.url().newBuilder(url);
            for (String key : queryMap.keySet()) {
                String value = queryMap.get(key);
                httpUrlBuilder.addQueryParameter(key, value);
            }
            request = request.newBuilder().url(httpUrlBuilder.build()).build();
        }
        ////L.i("===========queries============"+queries.toString());

        return chain.proceed(request);
    }

    public BaseInterceptor addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public BaseInterceptor addQuery(String name, String value) {
        queries.put(name, value);
        return this;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public HashMap<String, String> getQuerys() {
        return queries;
    }

}