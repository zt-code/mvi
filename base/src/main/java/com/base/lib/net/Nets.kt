package com.zt.mvvm.net

class Nets {

    companion object {
        //val ins: Nets by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Nets() }
        private var mMap: HashMap<String, Net> = mutableMapOf<String, Net>() as HashMap<String, Net>

        fun get(key: String): Net {
            if(mMap.containsKey(key)) {
                return mMap[key]!!;
            }else{
                var net = Net()
                mMap[key] = net
                return net
            }
        }
    }

    init {

    }

}