package com.zt.mvi.demo

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.base.lib.base.Tag
import com.base.lib.base.base_frg.OnFragmentResult
import com.base.lib.net.L
import com.zt.mvi.R
import com.zt.mvi.databinding.FrgDemoBinding
import com.zt.mvi.demo.bean.IntTag
import com.zt.mvi.demo.refresh.RefreshViewModelFactory
import com.zt.mvi.demo.refresh.RefreshViewModel
import com.zt.mvvm.base.frg.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
@Route(path = "/mvi/demo/fragment")
class FrgDemo: BaseFragment<FrgDemoBinding>() {

    @Inject
    lateinit var intTag: IntTag;

    private val viewModel: RefreshViewModel by lazy {
        ViewModelProvider(getAct(), RefreshViewModelFactory("FrgDemo")).get(RefreshViewModel::class.java)
        //ViewModelProvider(act, RefreshViewModelFactory("some string value")).get(RefreshViewModel::class.java)
        //ViewModelProvider(frg, RefreshViewModelFactory("some string value")).get(RefreshViewModel::class.java)
        //ViewModelProvider(applocation, RefreshViewModelFactory("some string value")).get(RefreshViewModel::class.java)
    };

    override fun getContentLayout(): Int {
        return R.layout.frg_demo
    }

    override fun initView(savedInstanceState: Bundle?) {
        //num = getData().getString("title");
        L.i("====initView  num  "+intTag.num)
        //var title = parentFragment?: getData().getString("title");

        var title = parentFragment?.let {
            getData().getString("title")
        }?: (intTag.num++).let { "${intTag.num}号葫芦" }

        L.i("====title  "+title)
        bind.textviewFirst.text = title
        bind.buttonFirst.setOnClickListener {
            var result = object : OnFragmentResult {
                override fun onResult(result: JSONObject) {
                    L.i("====你大爷哦哦哦  "+result.toString())
                    intTag.num = parentFragment?.let { 0 }?: intTag.num
                    result?.let {
                        bind.textviewFirst.text = it.getString("title")
                    }
                }
            }
            getAct().openFragment(FrgDemo().apply {
                mResult = result
                arguments = Bundle().apply {
                    putSerializable(Tag.FrgData, JSONObject().apply {
                        put("title", title);
                    })
                }
            })?.commitAllowingStateLoss()
        }

        bind.buttonClose.setOnClickListener {
            setResult(JSONObject().apply {
                put("title", title)
            })
            getAct().onBackPressed();
        }

        /*L.i("==========="+viewModel.strType)
        L.i("==========="+viewModel.str)*/
        viewModel.strType = "FrgDemo";
        viewModel.str = "我是你弟弟哦"
    }

    override fun onDestroy() {
        super.onDestroy()
        L.i("====FrgDemo  onDestroy")
    }

}