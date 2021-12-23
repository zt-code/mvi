package com.zt.mvi.demo.viewpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.base.lib.base.Tag
import com.base.lib.base.base_frg.OnFragmentResult
import com.base.lib.net.L
import com.base.lib.view.viewpager.BaseFrgStatePagerAdapter
import com.zt.mvi.R
import com.zt.mvi.databinding.FrgDemoBinding
import com.zt.mvi.databinding.FrgViewPagerBinding
import com.zt.mvi.demo.FrgDemo
import com.zt.mvi.demo.bean.IntTag
import com.zt.mvi.demo.refresh.RefreshViewModelFactory
import com.zt.mvi.demo.refresh.RefreshViewModel
import com.zt.mvvm.base.frg.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FrgViewPager: BaseFragment<FrgViewPagerBinding>() {

    @Inject
    lateinit var intTag: IntTag;

    private var fragments: ArrayList<Fragment> = ArrayList();
    private val tabs: JSONArray by lazy {
        JSONArray().apply {
            add("大娃")
            add("二娃")
            add("三娃")
            add("四娃")
            add("五娃")
            add("六娃")
            add("七娃")
        }
    };

    private val viewModel: RefreshViewModel by lazy {
        ViewModelProvider(getAct(), RefreshViewModelFactory("FrgDemo")).get(RefreshViewModel::class.java)
        //ViewModelProvider(act, RefreshViewModelFactory("some string value")).get(RefreshViewModel::class.java)
        //ViewModelProvider(frg, RefreshViewModelFactory("some string value")).get(RefreshViewModel::class.java)
        //ViewModelProvider(applocation, RefreshViewModelFactory("some string value")).get(RefreshViewModel::class.java)
    };

    override fun getContentLayout(): Int {
        return R.layout.frg_view_pager
    }

    override fun initView(savedInstanceState: Bundle?) {
        for (i in 0 until 7) {
            var result = object : OnFragmentResult {
                override fun onResult(result: JSONObject) {
                    L.i("====你大爷哦哦哦  "+result.toString())
                    //setResult(result)
                }
            }
            fragments.add(FrgDemo().apply {
                mResult = result
                arguments = Bundle().apply {
                    putSerializable(Tag.FrgData, JSONObject().apply {
                        put("title", "${i+1}号葫芦");
                    })
                }
            })
        }

        bind.vp.offscreenPageLimit = 1
        bind.vp.adapter = BaseFrgStatePagerAdapter(
            childFragmentManager, FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT,
            fragments,
            tabs
        )
        bind.tabLayout.setupWithViewPager(bind.vp)

        bind.tvClose.setOnClickListener {
            setResult(JSONObject().apply {
                put("title", "来自FrgViewPager的回调");
            })
            getAct().onBackPressed()
        }

        viewModel.strType = "FrgDemo";
        viewModel.str = "我是你弟弟哦"
    }

}