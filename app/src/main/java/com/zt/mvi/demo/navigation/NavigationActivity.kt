package com.zt.mvi.demo.navigation

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.base.lib.base.base_act.MyFragmentNavigator
import com.base.lib.net.L
import com.zt.mvi.R
import com.zt.mvi.databinding.ActivityNavigationBinding
import com.zt.mvvm.base.act.BaseActivity
import com.zt.mvvm.base.frg.BaseFragment

class NavigationActivity : BaseActivity<ActivityNavigationBinding>() {
    override fun getContentLayout(): Int {
        return R.layout.activity_navigation;
    }

    override fun initView(savedInstanceState: Bundle?) {
        /*val appBarConfiguration = AppBarConfiguration(
            setOf(
            R.id.navigation_frg1, R.id.navigation_frg2,
            R.id.navigation_frg3, R.id.navigation_frg4)
        )*/

        //findNavController(R.id.nav_host_fragment_activity_main)
        val navController = findNavController(R.id.nav_host_fragment_activity_main);



        val appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build();
        //val appBarConfiguration = AppBarConfiguration.Builder(bind.navView.menu).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bind.navView, navController);

        bind.navView.setOnNavigationItemSelectedListener {
            it.onNavDestinationSelected(navController).run {
                when (it.itemId) {
                    R.id.navigation_frg1_dest -> L.i("====  "+R.id.navigation_frg1_dest).run { false }
                    R.id.navigation_frg2_dest -> L.i("====  "+R.id.navigation_frg2_dest).run { false }
                    R.id.navigation_frg3_dest -> L.i("====  "+R.id.navigation_frg3_dest).run { false }
                    R.id.navigation_frg4_dest -> L.i("====  "+R.id.navigation_frg4_dest).run { false }
                    else -> L.i("==== ${it.itemId}").run { false }
                }
            }
        }




    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
        L.i("11111111111111")
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val currentDestination = navController.currentDestination  // 得到当前目的地
        if (currentDestination is MyFragmentNavigator.Destination){  //对Destination进行强制转换
            when (currentDestination.className){
                //如果当前页是SplashFragment或StartFragment或MainFragment则直接退出Activity
                BaseFragment::class.java.name -> super.onBackPressed()
                else -> super.onBackPressed()
            }
        }else {
            super.onBackPressed()
        }
        L.i("222222222222")
    }

    /*override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true;
    }*/

}