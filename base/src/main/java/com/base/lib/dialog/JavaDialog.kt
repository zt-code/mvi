package com.base.lib.dialog

import android.app.Dialog
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.alibaba.fastjson.JSONObject
import com.base.lib.R
import com.base.lib.net.L
import java.lang.Exception

class JavaDialog<T : ViewDataBinding?> : DialogFragment {
    private var bind: T? = null
    private var mListener: BaseDlgListener<T>? = null;
    private var mLayout: Int
    private var mJson: JSONObject? = null

    fun interface BaseDlgListener<T> {
        fun onDialogLayout(bind: T?, dialog: Dialog)
    }

    constructor(layout: Int, listener: BaseDlgListener<T>?) {
        mLayout = layout
        mListener = listener
    }

    constructor(layout: Int, json: JSONObject?, listener: BaseDlgListener<T>?) {
        mLayout = layout
        mJson = json
        mListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bind = DataBindingUtil.inflate<T>(LayoutInflater.from(context), mLayout, null, false)
        val dialog = Dialog(requireContext(), R.style.MyDialog)
        bind?.let {
            dialog.setContentView(it.root)
            dialog.setCancelable(true) // 设置点击屏幕Dialog消失
            dialog.setCanceledOnTouchOutside(true)
            mListener?.onDialogLayout(it, dialog);
        }
        return dialog
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val fragments = manager.fragments
        var hasFragment: Fragment? = null
        if (fragments != null) {
            val size = fragments.size
            for (i in 0 until size) {
                val fragment = fragments[i]
                if (fragment.tag != null) {
                    if(tag.equals(fragment.tag)) {
                        hasFragment = fragment
                        break
                    }
                }
            }
        }

        try {
            val c = Class.forName("androidx.fragment.app.DialogFragment")
            val con = c.getConstructor()
            val obj = con.newInstance()
            val dismissed = c.getDeclaredField("mDismissed")
            dismissed.isAccessible = true
            dismissed[obj] = false
            val shownByMe = c.getDeclaredField("mShownByMe")
            shownByMe.isAccessible = true
            shownByMe[obj] = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val ft = manager.beginTransaction()
        if (hasFragment != null) {
            L.i("删除上一个弹框")
            ft.remove(hasFragment)
        }
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }

}