package com.zt.mvi

import android.app.Application
import androidx.lifecycle.ViewModelStoreOwner
import com.alibaba.android.arouter.launcher.ARouter
import com.base.lib.BaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
public class MyApp : BaseApp() {

    companion object {
        lateinit var instance: MyApp

        fun get(): MyApp {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ARouter.init(this);
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }

}