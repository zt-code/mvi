package com.zt.module1

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.base.lib.net.L
import com.zt.common.bean.User
import com.zt.module1.databinding.FrgModule1Binding
import com.zt.mvvm.base.frg.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Route(path = "/module1_frg/main")
@AndroidEntryPoint
class FrgModule1 : BaseFragment<FrgModule1Binding>() {

    @Inject
    lateinit var user: User

    override fun getContentLayout(): Int {
        return R.layout.frg_module1
    }

    override fun initView(savedInstanceState: Bundle?) {
        L.i("====user  "+user.num)
        user.num++

        bind.tvBt.setOnClickListener {
            val fragment: BaseFragment<*> = ARouter.getInstance().build("/module2_frg/main").navigation() as BaseFragment<*>
            getAct().openFragment(/*FrgViewPager()*/fragment.apply {
            })?.commitAllowingStateLoss();
        }
    }

}