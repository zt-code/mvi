package com.base.lib.net.factory

import NetState
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.base.lib.net.L
import com.base.lib.net.bean.BaseBody
import com.base.lib.net.bean.BaseResult
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type







class CoroutineCallAdapterFactory : CallAdapter.Factory() {



    companion object {
        @JvmStatic @JvmName("create")
        operator fun invoke() = CoroutineCallAdapterFactory()
        const val tag = "CoroutineCallAdapterFactory";
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Deferred::class.java != getRawType(returnType)) {
            return null
        }
        if (returnType !is ParameterizedType) {
            throw IllegalStateException(
                "Deferred return type must be parameterized as Deferred<Foo> or Deferred<out Foo>")
        }
        val responseType = getParameterUpperBound(0, returnType)

        return when (getRawType(responseType)) {
            NetState::class.java -> {
                if (responseType !is ParameterizedType) {
                    throw IllegalStateException("Response must be parameterized as NetState<Foo> or NetState<out Foo>")
                }
                L.i(tag,"NetStateCallAdapter    $responseType")
                NetStateCallAdapter<Any>(getParameterUpperBound(0, responseType))

            }
            Response::class.java -> {
                if (responseType !is ParameterizedType) {
                    throw IllegalStateException("Response must be parameterized as Response<Foo> or Response<out Foo>")
                }
                L.i("BodyCallAdapter")
                ResponseCallAdapter<Any>(responseType)
            }
            else -> {
                BodyCallAdapter<Any>(responseType)
            }
        }
    }

    private class BodyCallAdapter<T>(
        private val responseType: Type
    ) : CallAdapter<T, Deferred<T>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Deferred<T> {
            val deferred = CompletableDeferred<T>()

            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                }
            }

            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    deferred.completeExceptionally(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        deferred.complete(response.body()!!)
                    } else {
                        deferred.completeExceptionally(HttpException(response))
                    }
                }
            })

            return deferred
        }
    }

    private class ResponseCallAdapter<T>(
        private val responseType: Type
    ) : CallAdapter<T, Deferred<Response<T>>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Deferred<Response<T>> {
            val deferred = CompletableDeferred<Response<T>>()

            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    //L.i("====ResponseCallAdapter  invokeOnCompletion  取消回调")
                    call.cancel()
                }
            }

            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    if(deferred.isCancelled) {
                        //L.i("====ResponseCallAdapter  onFailure  isCancelled  取消回调")
                        return
                    }
                    deferred.completeExceptionally(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if(deferred.isCancelled) {
                        //L.i("====ResponseCallAdapter  onResponse  isCancelled  取消回调")
                        return
                    }
                    deferred.complete(response)
                }
            })

            return deferred
        }
    }

    private class NetStateCallAdapter<T>(
        private val responseType: Type
    ) : CallAdapter<T, Deferred<NetState<T>>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Deferred<NetState<T>> {
            val deferred = CompletableDeferred<NetState<T>>()

            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                }
            }

            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    L.i(tag,"====onFailure  ${t.message}")
                    deferred.complete(NetState.Error(JSONObject().apply {
                        put("error", t.message.toString())
                    }))
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body() as T
                    if(body is BaseBody) {
                        val json = JSONObject.parseObject(body.str);
                        if(json.containsKey("data") && json.getIntValue("code") == 0) {
                            try {
                                val bean = JSON.parseObject(body.str, body.type) as T;
                                L.i(tag,"====bean    "+bean.toString())
                                deferred.complete(NetState.Success(bean))
                            }catch (e: Exception) {
                                L.i(tag,"====bean e code !=0    $json")
                                deferred.complete(NetState.Error(JSONObject().apply {
                                    put("code", json.getString("code"))
                                    put("data", json.getString("data"))
                                }))
                            }
                        }else {
                            L.i(tag,"====bean code !=0    $json")
                            deferred.complete(NetState.Error(JSONObject().apply {
                                put("code", json.getString("code"))
                                put("error", json.getString("error"))
                                put("msg", json.getString("msg"))
                            }))
                        }
                    }
                }
            })

            return deferred
        }
    }

}