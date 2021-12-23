package com.base.lib.base.base_frg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.base.lib.net.L

abstract class BindFragment<V : ViewDataBinding> : Fragment(){

    private val isSupportHiddenTag = "STATE_SAVE_IS_HIDDEN"

    private var _bind: V? = null
    val bind get() = _bind!!
    private var mContentView: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // fragment 重影解决方案：自己保存Fragment的Hidden状态
        if (savedInstanceState != null) {
            val isSupportHidden = savedInstanceState.getBoolean(isSupportHiddenTag);
            val ft = parentFragmentManager.beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //初始化binding
        mContentView?.let { content ->
            content.parent?.let { parent ->
                L.i("缓存的ContentView需要判断是否已有parent， 如果有parent需要从parent删除，否则会抛出异常")
                (parent as ViewGroup).removeView(content)
            }
            _bind = DataBindingUtil.getBinding(content);
            _bind?.lifecycleOwner = this

        }?: initRootView(inflater, container)
        return mContentView;
    }

    protected open fun initRootView(inflater: LayoutInflater, container: ViewGroup?) {
        _bind = DataBindingUtil.inflate(inflater, getContentLayout(), container, false)
        mContentView = _bind?.root as ViewGroup
        _bind?.lifecycleOwner = this
    }

    protected abstract fun getContentLayout(): Int

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(isSupportHiddenTag, isHidden);
    }

    open fun isBackPressed(): Boolean {
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind?.unbind()
        _bind = null
    }

}