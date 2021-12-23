package com.base.lib

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.base.lib.base.base_act.ActManager
import com.base.lib.net.interceptor.SameRequestFilterInterceptor
import com.base.lib.utils.Utils
import okhttp3.Request

open class BaseApp : Application(), ViewModelStoreOwner {

    private var mAppViewModelStore: ViewModelStore? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        setApplication(instance);
        mAppViewModelStore = ViewModelStore()
    }

    companion object {
        lateinit var instance: BaseApp

        fun get(): BaseApp {
            return instance
        }

        /**
         * 当主工程没有继承BaseApplication时，可以使用setApplication方法初始化BaseApplication
         *
         * @param application
         */
        @Synchronized
        fun setApplication(application: Application) {
            //初始化工具类
            Utils.init(application)
            //拦截器配置
            SameRequestFilterInterceptor.config(true, true, object : SameRequestFilterInterceptor.IConfig {
                override fun shouldFilter(url: String): Boolean {
                    return true
                }

                override fun generateCacheKey(request: Request): String {
                    return request.url.toString()
                }

                override fun responseCacheTimeInMills(): Long {
                    return 60000
                }
            })
            //注册监听每个activity的生命周期,便于堆栈式管理
            application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    ActManager.add(activity);
                }

                override fun onActivityStarted(activity: Activity) {}
                override fun onActivityResumed(activity: Activity) {}
                override fun onActivityPaused(activity: Activity) {}
                override fun onActivityStopped(activity: Activity) {}
                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
                override fun onActivityDestroyed(activity: Activity) {
                    ActManager.remove(activity);
                }
            })
        }

    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore!!;
    }

}