package com.zt.mvi.demo.navigation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.zt.mvi.R
import com.zt.mvi.databinding.FrgNavigation4Binding
import com.zt.mvvm.base.frg.BaseFragment

class FrgNavigation4: BaseFragment<FrgNavigation4Binding>() {

    override fun getContentLayout(): Int {
        return R.layout.frg_navigation4
    }

    override fun initView(savedInstanceState: Bundle?) {
        bind.bt.setOnClickListener {
            val bundle = FrgNavigation5Args("我是FrgNavigation4 传递过来的参数").toBundle();
            findNavController(this).navigate(R.id.navigation_frg5_dest, bundle)
        }
    }

}