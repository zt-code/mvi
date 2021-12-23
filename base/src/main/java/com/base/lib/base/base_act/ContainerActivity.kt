package com.zt.mvvm.base.act

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.base.lib.R
import com.base.lib.base.Tag
import com.base.lib.databinding.ActivityContainerBinding
import com.zt.mvvm.base.frg.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class ContainerActivity : BaseActivity<ActivityContainerBinding>() {

    private var targetFragment: Fragment? = null

    override fun getContentLayout(): Int {
        return R.layout.activity_container
    }

    public override fun initView(savedInstanceState: Bundle?) {
        addFrg()
    }

    private fun addFrg() {
        //获取Frg
        getIntentData()?.let { data ->
            data.frg?.let { frg ->
                //实例化frg
                targetFragment = (frg as Class).newInstance() as Fragment?;
                targetFragment?.let { target ->
                    //初始化frg数据
                    data?.frgParams.let {
                        val bundle = Bundle()
                        bundle.putSerializable(Tag.FrgData, data.frgParams);
                        target.arguments = bundle
                    }

                    //添加frg至act
                    val trans = supportFragmentManager.beginTransaction()
                    trans.add(
                        R.id.container,
                        target,
                        target.javaClass.name
                    ) //准备打开新的Fragment
                    trans.commitAllowingStateLoss()
                }
            }
        };
    }

    fun getTargetFragment(): Fragment? {
        return targetFragment;
    }

}