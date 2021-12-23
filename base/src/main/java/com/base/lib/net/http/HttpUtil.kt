package com.base.lib.net.http

import kotlin.jvm.JvmOverloads
import kotlin.Throws
import com.base.lib.net.http.HttpUtil
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.HashMap

object HttpUtil {
    /**
     * Send a get request
     * @param url         Url as string
     * @param headers     Optional map with headers
     * @return response   Response as string
     * @throws IOException
     */
    /**
     * Send a get request
     * @param url
     * @return response
     * @throws IOException
     */
    @JvmOverloads
    @Throws(IOException::class)
    operator fun get(url: String,
        headers: Map<String, String>? = null
    ): String {
        return fetch("GET", url, null, headers)
    }
    /**
     * Send a post request
     * @param url         Url as string
     * @param body        Request body as string
     * @param headers     Optional map with headers
     * @return response   Response as string
     * @throws IOException
     */
    /**
     * Send a post request
     * @param url         Url as string
     * @param body        Request body as string
     * @return response   Response as string
     * @throws IOException
     */
    @JvmOverloads
    @Throws(IOException::class)
    fun post(
        url: String?, body: String?,
        headers: Map<String, String>? = null
    ): String {
        return fetch("POST", url, body, headers)
    }

    /**
     * Post a json string
     * @param url         Url as string
     * @param jsonStr     a json string
     * @return response   Response as string
     * @throws IOException
     */
    @Throws(IOException::class)
    fun postJson(url: String?, jsonStr: String?): String {
        val headers: MutableMap<String, String> = HashMap()
        headers["Content-Type"] = "application/json;charset=UTF-8"
        return post(url, jsonStr, headers)
    }
    /**
     * Post a form with parameters
     * @param url         Url as string
     * @param params      Map with parameters/values
     * @param headers     Optional map with headers
     * @return response   Response as string
     * @throws IOException
     */
    /**
     * Post a form with parameters
     * @param url         Url as string
     * @param params      map with parameters/values
     * @return response   Response as string
     * @throws IOException
     */
    @JvmOverloads
    @Throws(IOException::class)
    fun postForm(
        url: String?, params: Map<String, String>?,
        headers: MutableMap<String, String>? = null
    ): String {
        // set content type
        var headers = headers
        if (headers == null) {
            headers = HashMap()
        }
        headers["Content-Type"] = "application/x-www-form-urlencoded"

        // parse parameters
        var body: String? = ""
        if (params != null) {
            var first = true
            for (param in params.keys) {
                if (first) {
                    first = false
                } else {
                    body += "&"
                }
                val value = params[param]
                body += URLEncoder.encode(param, "UTF-8") + "="
                body += URLEncoder.encode(value, "UTF-8")
            }
        }
        return post(url, body, headers)
    }
    /**
     * Send a put request
     * @param url         Url as string
     * @param body        Request body as string
     * @param headers     Optional map with headers
     * @return response   Response as string
     * @throws IOException
     */
    /**
     * Send a put request
     * @param url         Url as string
     * @return response   Response as string
     * @throws IOException
     */
    @JvmOverloads
    @Throws(IOException::class)
    fun put(
        url: String?, body: String?,
        headers: Map<String, String>? = null
    ): String {
        return fetch("PUT", url, body, headers)
    }
    /**
     * Send a delete request
     * @param url         Url as string
     * @param headers     Optional map with headers
     * @return response   Response as string
     * @throws IOException
     */
    /**
     * Send a delete request
     * @param url         Url as string
     * @return response   Response as string
     * @throws IOException
     */
    @JvmOverloads
    @Throws(IOException::class)
    fun delete(
        url: String?,
        headers: Map<String, String>? = null
    ): String {
        return fetch("DELETE", url, null, headers)
    }

    /**
     * Append query parameters to given url
     * @param url         Url as string
     * @param params      Map with query parameters
     * @return url        Url with query parameters appended
     * @throws IOException
     */
    @Throws(IOException::class)
    fun appendQueryParams(
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

    /**
     * Retrieve the query parameters from given url
     * @param url         Url containing query parameters
     * @return params     Map with query parameters
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getQueryParams(url: String): Map<String, String> {
        val params: MutableMap<String, String> = HashMap()
        var start = url.indexOf('?')
        while (start != -1) {
            // read parameter name
            val equals = url.indexOf('=', start)
            var param = ""
            param = if (equals != -1) {
                url.substring(start + 1, equals)
            } else {
                url.substring(start + 1)
            }

            // read parameter value
            var value = ""
            if (equals != -1) {
                start = url.indexOf('&', equals)
                value = if (start != -1) {
                    url.substring(equals + 1, start)
                } else {
                    url.substring(equals + 1)
                }
            }
            params[URLDecoder.decode(param, "UTF-8")] = URLDecoder.decode(value, "UTF-8")
        }
        return params
    }

    /**
     * Returns the url without query parameters
     * @param url         Url containing query parameters
     * @return url        Url without query parameters
     * @throws IOException
     */
    @Throws(IOException::class)
    fun removeQueryParams(url: String): String {
        val q = url.indexOf('?')
        return if (q != -1) {
            url.substring(0, q)
        } else {
            url
        }
    }

    /**
     * Send a request
     * @param method      HTTP method, for example "GET" or "POST"
     * @param url         Url as string
     * @param body        Request body as string
     * @param headers     Optional map with headers
     * @return response   Response as string
     * @throws IOException
     */
    @Throws(IOException::class)
    fun fetch(
        method: String?, url: String?, body: String?,
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

    /**
     * Read an input stream from conn into a string
     * @param in
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun streamToString(`in`: InputStream?): String {
        val isr = InputStreamReader(`in`, "UTF-8")
        val br = BufferedReader(isr)
        var data: String?
        val out = StringBuilder()
        while (br.readLine().also { data = it } != null) {
            out.append(data)
        }
        br.close()
        return out.toString()
    }
}