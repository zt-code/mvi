package com.zt.mvi.demo.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.zt.mvi.R
import com.zt.mvi.databinding.FrgNavigation4Binding
import com.zt.mvi.databinding.FrgNavigation5Binding
import com.zt.mvvm.base.frg.BaseFragment

class FrgNavigation5: BaseFragment<FrgNavigation5Binding>() {

    override fun getContentLayout(): Int {
        return R.layout.frg_navigation5
    }

    @SuppressLint("RestrictedApi")
    override fun initView(savedInstanceState: Bundle?) {


        bind.bt.setOnClickListener {
            //val bundle = FrgNavigation1Args("我是FrgNavigation5 传递过来的参数").toBundle();
            //findNavController().navigate(R.id.navigation_frg1_dest, bundle)
            //findNavController().navigateUp()
            //findNavController().navigateUp()
            //activity?.findNavController(R.id.navigation_frg4_dest)?.navigateUp();、
            findNavController(this).popBackStack(R.id.navigation_frg5_dest, true)
        }
    }

}