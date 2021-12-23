package com.zt.mvi.demo.navigation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.base.lib.utils.ToastUtil
import com.zt.mvi.R
import com.zt.mvi.databinding.FrgNavigation2Binding
import com.zt.mvvm.base.frg.BaseFragment

class FrgNavigation2: BaseFragment<FrgNavigation2Binding>() {

    override fun getContentLayout(): Int {
        return R.layout.frg_navigation2
    }

    override fun initView(savedInstanceState: Bundle?) {
        bind.bt.setOnClickListener {
            val bundle = FrgNavigation3Args("我是FrgNavigation2 传递过来的参数").toBundle();
            findNavController(this).navigate(R.id.navigation_frg3_dest, bundle)
        }

        //dataInit();
    }

    private fun dataInit(){
        val bundle = arguments
        if (bundle != null){
            val userName = arguments?.let { FrgNavigation2Args.fromBundle(it).taskId }
            ToastUtil.getIns().show(context, userName)
        }
    }

}