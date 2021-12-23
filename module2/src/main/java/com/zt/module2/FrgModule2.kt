package com.zt.module2

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.base.lib.net.L
import com.zt.common.bean.User
import com.zt.module2.databinding.FrgModule2Binding
import com.zt.mvvm.base.frg.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Route(path = "/module2_frg/main")
@AndroidEntryPoint
class FrgModule2 : BaseFragment<FrgModule2Binding>() {

    @Inject
    lateinit var user: User

    override fun getContentLayout(): Int {
        return R.layout.frg_module2
    }

    override fun initView(savedInstanceState: Bundle?) {
        L.i("====user  "+user.num)
        user.num++

        bind.tvBt.setOnClickListener {
            val fragment: BaseFragment<*> = ARouter.getInstance().build("/module3_frg/main").navigation() as BaseFragment<*>
            getAct().openFragment(/*FrgViewPager()*/fragment.apply {
            })?.commitAllowingStateLoss();
        }
    }

}