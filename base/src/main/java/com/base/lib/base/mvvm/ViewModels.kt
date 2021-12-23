package com.base.lib.base.mvvm

import androidx.lifecycle.ViewModel


public class ViewModels {

    companion object {
        //val ins: Nets by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Nets() }
        private var mMap: HashMap<String, ViewModel> = mutableMapOf<String, ViewModel>() as HashMap<String, ViewModel>

        fun get(key: String): ViewModel? {
            return mMap[key];
        }

        fun set(key: String, vm: ViewModel): ViewModel? {
            mMap[key] = vm;
            return mMap[key];
        }
    }

}