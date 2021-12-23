package com.zt.module3

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.base.lib.net.L
import com.zt.common.bean.User
import com.zt.module3.databinding.FrgModule3Binding
import com.zt.mvvm.base.frg.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Route(path = "/module3_frg/main")
@AndroidEntryPoint
class FrgModule3 : BaseFragment<FrgModule3Binding>() {

    @Inject
    lateinit var user: User

    override fun getContentLayout(): Int {
        return R.layout.frg_module3
    }

    override fun initView(savedInstanceState: Bundle?) {
        L.i("====user  "+user.num)
        user.num++

        bind.tvBt.setOnClickListener {
            ARouter.getInstance().build("/module3/main").navigation()
        }
    }

}