package com.zt.module2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.base.lib.base.base_act.BaseFrgActivity
import com.base.lib.net.L
import com.zt.module2.databinding.ActivityModule2MainBinding
import com.zt.mvvm.base.act.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Route(path = "/module2/main")
@AndroidEntryPoint
class Module2MainActivity : BaseFrgActivity<ActivityModule2MainBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        bind.tvBt.setOnClickListener {
            ARouter.getInstance().build("/module1/main").navigation()
        }
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_module2_main
    }
}