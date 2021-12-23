package com.base.lib.net.http

import android.content.Context
import kotlinx.coroutines.*

object HttpHelper {

    /**
     * 直接获取结果的
     */
    fun <T> async (
        request: suspend CoroutineScope.() -> T,
        success: (T) -> Unit,
        error: suspend CoroutineScope.(T) -> Unit = {},
        complete: suspend CoroutineScope.() -> Unit = {}
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            delay(2000)
            success.invoke(request(this))
        }
    }





    /*fun get(url: String, queryMap: HashMap<String, String>?, headers: Map<String, String>?) {
        fetch("get", appendQueryParams(url, queryMap), headers)
    }





    fun fetch(
        method: String?,
        url: String?,
        headers: Map<String, String>?
    ): String {
        // connection
        val u = URL(url)
        val conn = u.openConnection() as HttpURLConnection
        conn.connectTimeout = 120000
        conn.readTimeout = 120000

        // method
        if (method != null) {
            conn.requestMethod = method
        }

        // headers
        if (headers != null) {
            for (key in headers.keys) {
                conn.addRequestProperty(key, headers[key])
            }
        }

        // body
        if (body != null) {
            conn.doOutput = true
            val os = conn.outputStream
            os.write(body.toByteArray())
            os.flush()
            os.close()
        }

        // response
        val `is` = conn.inputStream
        val response = streamToString(`is`)

        // handle redirects
        if (conn.responseCode == 301) {
            val location = conn.getHeaderField("Location")
            return fetch(method, location, body, headers)
        }
        return response
    }

    private fun appendQueryParams(
        url: String,
        params: Map<String, String>?
    ): String {
        var fullUrl = url
        if (params != null) {
            var first = fullUrl.indexOf('?') == -1
            for (param in params.keys) {
                if (first) {
                    fullUrl += '?'
                    first = false
                } else {
                    fullUrl += '&'
                }
                val value = params[param]
                fullUrl += URLEncoder.encode(param, "UTF-8") + '='
                fullUrl += URLEncoder.encode(value, "UTF-8")
            }
        }
        return fullUrl
    }

    private fun streamToString(input: InputStream?): String {
        var isr:InputStreamReader? = null;
        var br:BufferedReader? = null;
        var out:StringBuilder? = null;

        try{
            isr = InputStreamReader(input, "UTF-8")
            br = BufferedReader(isr)
            out = StringBuilder()

            var data: String?
            while (br.readLine().also { data = it } != null) {
                out.append(data)
            }
        }catch (e: Exception) {
            e.printStackTrace()
            out = null;
        }finally {
            br?.close()
            isr?.close()
        }

        return out?.toString() ?: ""
    }*/

}