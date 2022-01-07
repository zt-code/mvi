package com.zt.dex

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.base.lib.utils.FixDexUtils
import com.base.lib.utils.down.DownHelper
import com.base.lib.utils.down.DownManager

/*@HiltAndroidApp*/
class DexApp : Application() {

    companion object {
        lateinit var instance: DexApp

        fun get(): DexApp {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ARouter.init(this);
        DownManager.init(this);
    }

    override fun attachBaseContext(base: Context?) {
        MultiDex.install(base)
        FixDexUtils.LoadFixedDex(base)
        super.attachBaseContext(base)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }

}