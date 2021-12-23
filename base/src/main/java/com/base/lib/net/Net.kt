package com.zt.mvvm.net

import com.alibaba.fastjson.JSONObject
import com.base.lib.net.factory.CoroutineCallAdapterFactory
import com.base.lib.net.factory.FastJsonConverterFactory
import com.base.lib.net.factory.NetStateConverterFactory
import com.base.lib.net.interceptor.BaseInterceptor
import com.base.lib.net.interceptor.LogInterceptor
import com.base.lib.net.interceptor.RetryAndChangeIpInterceptor
import com.base.lib.net.interceptor.SameRequestFilterInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

class Net {

    private var retrofit:Retrofit.Builder? = null
    private var client:OkHttpClient.Builder = OkHttpClient.Builder()
    private val baseInterceptor = BaseInterceptor()
    private val logInterceptor = LogInterceptor()
    /*private val logInterceptor = LoggingInterceptor.Builder()
        .loggable(true) // TODO: 发布到生产环境需要改成false
        .request()
        .requestTag("Request")
        .response()
        .responseTag("Response")
        //.hideVerticalLine()// 隐藏竖线边框
        .build()*/

    private val sameRequestFilterInterceptor = SameRequestFilterInterceptor()
    private val retryAndChangeIpInterceptor = RetryAndChangeIpInterceptor()
    private var interceptors: MutableMap<String, Interceptor> = mutableMapOf()

    private var mUrl:String? = null
    private var outTime = 15

    fun <T> create(service: Class<T>): T {
        //初始化 OkHttpClient
        //Log.i("why", "初始化 OkHttpClient")
        initClient()

        //初始化URL
        if (mUrl == null) mUrl = "https://chengshidianliang.net"
        return retrofit?.baseUrl(mUrl)?.client(client?.build())?.build()?.create(service)!!
    }

    private fun initClient(): OkHttpClient.Builder {

        var str = "{\"PLATFORM\":\"android\",\"ACCESS_TOKEN\":\"Wft056rTMOnH9JNhjDQ7VFu1dFca1cYr6QYZ\",\"MC\":\"channel_huawei\",\"OS_VERSION\":\"11\",\"User-Agent\":\"Mozilla/5.0 (Linux; Android 11; SM-G9750 Build/RP1A.200720.012; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/87.0.4280.141 Mobile Safari/537.36\",\"Client-IP\":\"115.236.35.106\",\"DEVICE_ID\":\"829e354b4523861256f5f2f35c3ba21\",\"APP_VERSION\":\"1.10.0\",\"Content-Type\":\"application/json\"}"
        var json = JSONObject.parse(str) as Map<String, String>;

        baseInterceptor.apply {
            json.forEach { (key,value) ->
                //Log.i("why", "====$key :  $value")
                addHeader(key, value)
            }
        }


        //if(cookieJar == null) cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mContext));
        //Cache cache = new Cache(mFile, 10 * 1024 * 1024);

        client = OkHttpClient.Builder().apply {
            connectTimeout((outTime * 1000).toLong(), TimeUnit.MILLISECONDS)
            readTimeout((outTime * 1000).toLong(), TimeUnit.MILLISECONDS)
            writeTimeout((outTime * 1000).toLong(), TimeUnit.MILLISECONDS)
            /*sslSocketFactory(
                SSLContextSecurity.createIgnoreVerifySSL("SSL"),
                SSLContextSecurity.TrustAllManager()
            )*/
            addInterceptor(logInterceptor) //日志拦截器
            addInterceptor(baseInterceptor) //Base参数

            //addInterceptor(sameRequestFilterInterceptor) //Base参数
            //addInterceptor(retryAndChangeIpInterceptor) //Base参数
            interceptors.forEach { (key:String, value:Interceptor)  ->
                addInterceptor(value)
            }
            hostnameVerifier(DO_NOT_VERIFY) //.proxy(Proxy.NO_PROXY) //反抓包
            retryOnConnectionFailure(true) //解决网络超时无效问题false; //允许失败重试
        }
        return client
    }

    fun setUrl(url: String): Net {
        mUrl = url
        return this
    }

    fun setOutTime(num: Int): Net {
        outTime = num
        return this
    }

    fun addHeader(key: String, value: String): Net {
        baseInterceptor.addHeader(key, value)
        return this
    }

    fun removeHeader(key: String): Net {
        baseInterceptor.headers.remove(key)
        return this
    }

    fun addQuery(key: String, value: String): Net {
        baseInterceptor.addQuery(key, value)
        return this
    }

    fun removeQuery(key: String): Net {
        baseInterceptor.querys.remove(key)
        return this
    }

    fun addInterceptor(interceptor: Interceptor): Net {
        val key = interceptor.javaClass.canonicalName
        if(!interceptors.containsKey(key)) {
            interceptors[key] = interceptor
        }
        return this
    }

    fun build(): Net {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(NetStateConverterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                //.addConverterFactory(ScalarsConverterFactory.create())
        }
        return this
    }

    val DO_NOT_VERIFY = HostnameVerifier { hostname, session -> true }

}