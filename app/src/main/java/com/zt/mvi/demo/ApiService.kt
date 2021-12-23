package com.zt.mvi.demo

import NetState
import com.alibaba.fastjson.JSONObject
import com.base.lib.net.bean.BaseResult
import com.zt.mvi.demo.bean.Data
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiService {

    @GET("/api/lightup/category/list")
    fun getLightUpList(): Deferred<String>

    @GET("/api/lightup/category/list")
    fun getLightList(): Deferred<NetState<BaseResult<List<Data>>>>

    @GET("/api/user/detail")
    fun getUserDetail(): Deferred<JSONObject>

    /*@GET("/api/n/adv/faster/customer/service/info")
    fun getCallData(): Call<String>

    @GET("/api/n/adv/faster/customer/service/info")
    fun getFlowableData(): Flowable<String>

    @GET("/api/n/adv/faster/customer/service/info")
    fun getDeferredData1(): Deferred<String>

    @GET("/api/n/adv/faster/customer/service/info")
    fun getDeferredData2(): Deferred<String>

    @GET("/api/n/adv/faster/customer/service/info")
    fun getDeferredData3(): Deferred<Response<String>>

    //获取用户详情信息
    @GET("/api/user/detail")
    fun getUserInfo(): Deferred<String>

    //发送登陆验证码
    @POST("/api/n/sms/faster/login")
    fun sendSms(@Body params: JSONObject): Deferred<String>

    //手机短信登录
    @POST("/api/n/user/login/faster/sms")
    fun loginSms(@Body params: JSONObject): Deferred<String>

    //获取首页banner数据
    @GET("/api/banner/list")
    fun getBanner(@QueryMap params: Map<String, String>): Deferred<String>

    //媒体列表
    @GET("/api/adv/faster/meida/list")
    fun getMeidaList(@QueryMap params: JSONObject): Deferred<String>

    //模板活动  //类型；3:教育,4:美业,5:,6:娱乐,7:餐饮,8:其他
    @GET("/api/n/activity/template/tag/list")
    fun getTempLateTagList(@QueryMap params: JSONObject): Deferred<String>

    //投放版位列表(旧)
    @GET("/api/adv/faster/location")
    fun getFasterLocationList(@QueryMap params: JSONObject): Deferred<String>

    //投放媒体列表
    @GET("/api/adv/faster/meida/new/list")
    fun getFasterMeidaNewList(@QueryMap params: JSONObject): Deferred<String>*/

}