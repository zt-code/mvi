package com.zt.mvi.demo

import android.os.Bundle
import com.zt.mvi.R
import com.zt.mvi.databinding.ActivityTabLayoutBinding
import com.zt.mvvm.base.act.BaseActivity

class TabLayoutActivity : BaseActivity<ActivityTabLayoutBinding>() {

    override fun getContentLayout(): Int {
        return R.layout.activity_tab_layout;
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

}