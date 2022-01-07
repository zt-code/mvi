package com.zt.socket

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.base.lib.utils.FixDexUtils
import com.base.lib.utils.down.DownHelper
import com.base.lib.utils.down.DownManager

/*@HiltAndroidApp*/
class SocketApp : Application() {

    companion object {
        lateinit var instance: SocketApp

        fun get(): SocketApp {
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