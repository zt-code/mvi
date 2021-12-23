package com.zt.module3

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.base.lib.base.base_act.BaseFrgActivity
import com.base.lib.net.L
import com.zt.common.bean.User
import com.zt.module3.databinding.ActivityModule3MainBinding
import com.zt.mvvm.base.frg.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@Route(path = "/module3/main")
@AndroidEntryPoint
class Module3MainActivity : BaseFrgActivity<ActivityModule3MainBinding>() {

    @Inject
    lateinit var user: User

    override fun initView(savedInstanceState: Bundle?) {
        L.i("====user  "+user.num)
        user.num++
        /*user.get();
        user.num++*/

        bind.tvBt.setOnClickListener {
            val fragment: BaseFragment<*> = ARouter.getInstance().build("/module1_frg/main").navigation() as BaseFragment<*>
            openFragment(/*FrgViewPager()*/fragment.apply {
            })?.commitAllowingStateLoss();
        }
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_module3_main
    }
}