package com.zt.common

import android.app.Application
import androidx.lifecycle.ViewModelStoreOwner
import com.alibaba.android.arouter.launcher.ARouter
import com.base.lib.BaseApp
import dagger.hilt.android.HiltAndroidApp

open class CommonApp : Application() {

    companion object {
        lateinit var instance: CommonApp

        fun get(): CommonApp {
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