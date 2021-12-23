package com.zt.module2

import android.app.Application
import androidx.lifecycle.ViewModelStoreOwner
import com.alibaba.android.arouter.launcher.ARouter
import com.base.lib.BaseApp
import com.zt.common.CommonApp
import dagger.hilt.android.HiltAndroidApp

/*@HiltAndroidApp*/
class Module2App : Application() {

    companion object {
        lateinit var instance: Module2App

        fun get(): Module2App {
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