package com.zt.mvvm.base.act

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.base.lib.BaseApp
import com.base.lib.R
import com.base.lib.base.IntentData
import com.base.lib.base.MIntent
import com.base.lib.base.MyUI
import com.base.lib.base.Tag
import com.base.lib.base.base_act.ActManager
import com.base.lib.base.glide.GlideUtil
import com.base.lib.base.statusbar.StatusBarUtil
import com.base.lib.net.L
import com.base.lib.utils.ToastUtil
import com.google.android.material.appbar.AppBarLayout
import com.zt.mvvm.mvvm.BaseVM
import java.util.*
import kotlin.collections.HashMap

open abstract class BaseActivity<V : ViewDataBinding> : BindActivity<V>(), MyUI {

    private var mActivityProvider: ViewModelProvider? = null;
    private var mApplicationProvider: ViewModelProvider? = null;

    private var params: JSONObject? = null;
    private var intentData: IntentData? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActManager.add(this)
        ARouter.getInstance().inject(this)

        //沉浸式状态栏
        StatusBarUtil.setTranslucentStatus(this@BaseActivity)

        //状态栏字体样式设置
        if (getStatusBarDark() > 0) {
            StatusBarUtil.setStatusBarDarkTheme(this, getStatusBarDark() == 1)
        }

        //获取数据
        initParams()

        //onCreate
        initView(savedInstanceState)
    }

    open fun getStatusBarDark(): Int { return -1 }

    protected abstract fun initView(savedInstanceState: Bundle?)

    override fun getLife(): LifecycleOwner { return this }

    override fun getUIManager(): FragmentManager { return supportFragmentManager; }

    override fun getData(): JSONObject { return params?:JSONObject(); }

    //TODO tip 2: Jetpack 通过 "工厂模式" 来实现 ViewModel 的作用域可控，
    //目前我们在项目中提供了 Application、Activity、Fragment 三个级别的作用域，
    //值得注意的是，通过不同作用域的 Provider 获得的 ViewModel 实例不是同一个，
    //所以如果 ViewModel 对状态信息的保留不符合预期，可以从这个角度出发去排查 是否眼前的 ViewModel 实例不是目标实例所致。
    //如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/6257931840
    protected open fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            L.i("getActivityScopeViewModel init")
            mActivityProvider = ViewModelProvider(this)
        }
        return mActivityProvider!!.get(modelClass)
    }

    protected open fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        if (mApplicationProvider == null) {
            L.i("getApplicationScopeViewModel init")
            mApplicationProvider = ViewModelProvider(this.applicationContext as BaseApp)
        }
        return mApplicationProvider!!.get(modelClass)
    }

    // ---------------------------------------------------------------------------------------------
    open fun getIntentData(): IntentData? {
        if(intentData == null) {
            if(intent.getSerializableExtra(Tag.UI) == null) {
                intentData = IntentData();
            }else{
                intentData = intent.getSerializableExtra(Tag.UI) as IntentData;
            }
        }
        return intentData;
    }

    open fun initParams() {
        params = getIntentData()?.actParams ?: JSONObject();
    }

    override fun onDestroy() {
        super.onDestroy()
        ActManager.remove(this)
        GlideUtil.clear()
    }

}