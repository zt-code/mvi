package com.zt.module3

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.lib.base.base_act.BaseFrgActivity
import com.base.lib.dialog.JavaDialog
import com.base.lib.net.L
import com.zt.common.bean.User
import com.zt.module3.databinding.ActivityModule3MainBinding
import com.zt.module3.databinding.DlgDemoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
            /*val fragment: BaseFragment<*> = ARouter.getInstance().build("/module1_frg/main").navigation() as BaseFragment<*>
            openFragment(*//*FrgViewPager()*//*fragment.apply {
            })?.commitAllowingStateLoss();*/

            lifecycleScope.launch {
                for(index in 0 .. 10) {
                    delay(100)
                    JavaDialog<DlgDemoBinding>(R.layout.dlg_demo) { bind, dialog ->
                        bind?.let {
                            //L.i("你大耶耶耶耶耶呃呃呃")
                            bind.tv.setText("你大耶耶耶耶耶呃呃呃")
                        } ?: L.i("你nullnullnullnullnullnullnullnull")
                    }.show(supportFragmentManager, "demo")
                }
            }
        }







    }

    override fun getContentLayout(): Int {
        return R.layout.activity_module3_main
    }
}