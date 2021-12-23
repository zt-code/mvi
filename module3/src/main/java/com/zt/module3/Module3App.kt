package com.zt.module3

import android.app.Application
import androidx.lifecycle.ViewModelStoreOwner
import com.alibaba.android.arouter.launcher.ARouter
import com.base.lib.BaseApp
import com.zt.common.CommonApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Module3App : CommonApp() {

    companion object {
        lateinit var instance: Module3App

        fun get(): Module3App {
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