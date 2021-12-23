package com.zt.module1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.base.lib.base.base_act.BaseFrgActivity
import com.base.lib.net.L
import com.zt.module1.databinding.ActivityModule1MainBinding
import com.zt.mvvm.base.act.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Route(path = "/module1/main")
@AndroidEntryPoint
class Module1MainActivity : BaseFrgActivity<ActivityModule1MainBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        bind.tvBt.setOnClickListener {
            ARouter.getInstance().build("/module3/main").navigation()
        }
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_module1_main
    }
}