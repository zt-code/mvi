package com.zt.mvvm.base.frg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.alibaba.android.arouter.launcher.ARouter
import com.alibaba.fastjson.JSONObject
import com.base.lib.R
import com.base.lib.base.MyUI
import com.base.lib.base.Tag
import com.base.lib.base.base_act.BaseFrgActivity
import com.base.lib.base.base_frg.BindFragment
import com.base.lib.base.base_frg.OnFragmentResult
import com.base.lib.base.statusbar.StatusBarUtil
import com.base.lib.net.L
import java.io.Serializable

open abstract class BaseFragment<V : ViewDataBinding> : BindFragment<V>(), MyUI {

    private var params: Serializable? = null;

    //var resultBundle: Bundle? = null;
    var mResult: OnFragmentResult? = null;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 状态栏字体样式设置
        if (getStatusBarDark() > 0) {
            StatusBarUtil.setStatusBarDarkTheme(activity, getStatusBarDark() == 1)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    open fun getStatusBarDark(): Int { return 1 }

    open fun initView(savedInstanceState: Bundle?) {}

    override fun getLife(): LifecycleOwner { return this }

    override fun getUIManager(): FragmentManager { return childFragmentManager; }

    override fun getData(): JSONObject { return params?.let { it as JSONObject } ?: JSONObject() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ARouter.getInstance().inject(this)
        initParams()
        initView(savedInstanceState)
    }

    /*override fun getContentLayout(): Int {
        return R.layout.activity_container;
    }*/

    open fun initParams() {
        params = arguments?.getSerializable(Tag.FrgData) ?: JSONObject()
        //L.i("====initParams  "+params.toString())
    }

    open fun getOpenAnim(): IntArray {
        return intArrayOf(
            R.anim.slide_right_in,
            R.anim.slide_left_out,
            R.anim.slide_left_in,
            R.anim.slide_right_out
        )
    }

    fun getAct() :BaseFrgActivity<*> {
        return activity as BaseFrgActivity<*>
    }

    fun setResult(result: JSONObject) {
        var frg = getRootFrg(this)
        (frg as BaseFragment<*>).let { root->
            root.params?.let {
                (it as JSONObject)[Tag.Result] = result
            }?: JSONObject().apply { put(Tag.Result, result) }
        }
        //L.i("====setResult  "+(params as JSONObject).toJSONString())
    }

    private fun getRootFrg(frg: Fragment) : Fragment {
        return frg.parentFragment?.let {
            getRootFrg(it)
        }?: frg
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveStateToArguments(outState)
    }

    private fun saveStateToArguments(bundle: Bundle?) {
        bundle?.let {
            it.putSerializable(Tag.FrgData, getData())
        }?:let {
            val b = Bundle()
            b.putSerializable(Tag.FrgData, getData())
            if(!isAdded) {
                arguments = b
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if(isRemoving) {
            L.i("====onPause  isRemoving");
            onResult();

        }else{
            L.i("====onPause");
        }
    }

    fun onResult() {
        params?.let {
            mResult?.let { result->
                L.i("====mResult != null  "+(params as JSONObject).toJSONString());
                (params as JSONObject).getJSONObject(Tag.Result)?.let {
                    result.onResult(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //L.i("onDestroyView  ${javaClass.name}" + getData().toString())
        saveStateToArguments(arguments)
    }

    override fun onDestroy() {
        super.onDestroy()
        //L.i("onDestroy  ${javaClass.name}" + getData().toString())
    }

}