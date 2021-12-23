package com.base.lib.base.base_act

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.base.lib.R
import com.base.lib.base.MyUI
import com.base.lib.base.base_frg.OnFragmentResult
import com.zt.mvvm.base.act.BaseActivity
import com.zt.mvvm.base.frg.BaseFragment

open abstract class BaseFrgActivity<V : ViewDataBinding> : BaseActivity<V>(){

    open fun openFragment(targetFragment: Fragment): FragmentTransaction? {
        val trans = supportFragmentManager.beginTransaction().addToBackStack(null)
        if(targetFragment is BaseFragment<*>) {
            val anim: IntArray = targetFragment.getOpenAnim()
            trans.setCustomAnimations(anim[0], anim[1], anim[2], anim[3])
        }
        if (!targetFragment.isAdded) {  //如果目标targetFragment没有add过
            trans.add(R.id.frg_content,
                targetFragment,
                targetFragment.javaClass.canonicalName
            ) //准备打开新的Fragment
        } else {  //如果目标targetFragment已经add过
            trans.show(targetFragment)
        }
        return trans
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frg_content)
        val frags = supportFragmentManager.fragments;
        if (fragment is BaseFragment<*>) {
            if (!fragment.isBackPressed()) {
                if (frags.size >= 1) {
                    fragment.onResult()
                    super.onBackPressed()
                } else {
                    finish()
                }
            }
        } else {
            super.onBackPressed()
        }
    }




}