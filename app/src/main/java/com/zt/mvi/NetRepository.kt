package com.zt.mvi


class NetRepository {

    /*companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: NetRepository? = null

        fun getIns() =
            instance ?: synchronized(this) {
                instance ?: NetRepository().also { instance = it }
            }
    }

    suspend fun getApi(): NetState<NetData> {
        val articlesApiResult = try {
            //api.await()
            var obj = JSONObject();
            obj.put("code", 0);
            var arr = JSONArray();
            for (index in 1..5) {
                var item = JSONObject();
                item.put("title", "哈哈哈"+index);
                arr.add(item);
            }
            obj.put("data", arr);
            obj.toString();
        } catch (e: Exception) {
            Log.i("why", "===========Exception====${e.message}")
            return NetState.Error(JSONObject().apply {
                put("msg", e.message)
            })
        }

        articlesApiResult?.let {
            Log.i("why", "==============let====${it}")
            val json = JSONObject.parseObject(it)
            val code = json.getIntValue("code")
            if(code == 0) {
                val data = json.get("data");
                if(data is JSONObject) {
                    return NetState.Success(data = NetData(code,0, 0,data.toString()))
                }else if(data is JSONArray) {
                    return NetState.Success(data = NetData(code,1, 0,data.toString()))
                }else{
                    return NetState.Success(data = NetData(code,2, 0,data.toString()))
                }
            }else{
                return NetState.Success(data = NetData(code,2, 0,it))
            }
        }
    }*/




}