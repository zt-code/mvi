package com.zt.mvi.demo.navigation

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.zt.mvi.R
import com.zt.mvi.databinding.FrgNavigation3Binding
import com.zt.mvvm.base.frg.BaseFragment

class FrgNavigation3: BaseFragment<FrgNavigation3Binding>() {

    override fun getContentLayout(): Int {
        return R.layout.frg_navigation3
    }

    override fun initView(savedInstanceState: Bundle?) {
        bind.bt.setOnClickListener {
            val bundle = FrgNavigation4Args("我是FrgNavigation3 传递过来的参数").toBundle();
            findNavController(this).navigate(R.id.navigation_frg4_dest, bundle)
        }
    }

}