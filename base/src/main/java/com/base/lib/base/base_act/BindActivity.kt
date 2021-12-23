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
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.base.lib.BaseApp
import com.base.lib.R
import com.base.lib.base.IntentData
import com.base.lib.base.MIntent
import com.base.lib.base.MyUI
import com.base.lib.base.Tag
import com.base.lib.base.glide.GlideUtil
import com.base.lib.base.statusbar.StatusBarUtil
import com.base.lib.net.L
import com.base.lib.utils.ToastUtil
import com.google.android.material.appbar.AppBarLayout
import com.zt.mvvm.mvvm.BaseVM
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.HashMap

open abstract class BindActivity<V : ViewDataBinding> : AppCompatActivity(), MyUI {

    private var _bind: V? = null
    val bind get() = _bind!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //防止重复打开Act
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }

        //初始化View
        initRootView()
    }

    protected abstract fun getContentLayout(): Int

    // ---------------------------------------------------------------------------------------------

    protected open fun initRootView() {
        _bind = DataBindingUtil.setContentView(this, getContentLayout())
        _bind?.lifecycleOwner = this
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind?.unbind()
        _bind = null
    }

}