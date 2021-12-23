package com.zt.mvi

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.alibaba.android.arouter.launcher.ARouter
import com.alibaba.fastjson.JSONObject
import com.base.lib.base.IntentData
import com.base.lib.base.Tag
import com.base.lib.base.base_act.BaseFrgActivity
import com.base.lib.base.base_frg.OnFragmentResult
import com.base.lib.net.L
import com.base.lib.net.bean.BaseResult
import com.base.lib.net.http.HttpHelper
import com.base.lib.observeState
import com.zt.mvi.databinding.ActivitySplashBinding
import com.zt.mvi.demo.FrgDemo
import com.zt.mvi.demo.bean.Data
import com.zt.mvi.demo.bean.IntTag
import com.zt.mvi.demo.refresh.MainViewState
import com.zt.mvi.demo.refresh.RefreshActivity
import com.zt.mvi.demo.refresh.RefreshViewModel
import com.zt.mvi.demo.refresh.RefreshViewModelFactory
import com.zt.mvi.demo.viewpage.FrgViewPager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import android.R.string.no
import com.alibaba.android.arouter.facade.annotation.Route
import com.zt.mvvm.base.frg.BaseFragment

@AndroidEntryPoint
@Route(path = "/mvi/splash")
class SplashActivity : BaseFrgActivity<ActivitySplashBinding>() {

    //private lateinit var viewModel: RefreshViewModel;
    @Inject
    lateinit var intTag: IntTag;

    private val viewModel: RefreshViewModel by lazy {
        //ViewModelProvider(this).get(RefreshViewModel::class.java)
        ViewModelProvider(this, RefreshViewModelFactory( "SplashActivity")).get(RefreshViewModel::class.java)
        //ViewModelProvider(frg, RefreshViewModelFactory("some string value")).get(RefreshViewModel::class.java)
        //ViewModelProvider(applocation, RefreshViewModelFactory("some string value")).get(RefreshViewModel::class.java)
    };

    override fun getContentLayout(): Int {
        return R.layout.activity_splash;
    }

    override fun initView(savedInstanceState: Bundle?) {
        //viewModel = ViewModelProvider(this, RefreshViewModelFactory("some string value")).get(RefreshViewModel::class.java)

        var str = "{\"PLATFORM\":\"android\",\"ACCESS_TOKEN\":\"0lTyGw7e2dCDJKprXa9kU1Tqu5okQGI4ZosD\",\"MC\":\"channel_huawei\",\"OS_VERSION\":\"11\",\"User-Agent\":\"Mozilla/5.0 (Linux; Android 11; SM-G9750 Build/RP1A.200720.012; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/87.0.4280.101 Mobile Safari/537.36\",\"OAID\":\"88da79cd292532627f45b403a547d4a07beddd38e0304fe362bc2dd938f7dc55\",\"DEVICE_ID\":\"efd94dea1a822899\",\"APP_VERSION\":\"1.8.2\",\"Content-Type\":\"application/json\"}"
        var json = JSONObject.parse(str) as Map<String, String>;
        HttpHelper.async({
            JSONObject().apply { put("title", "啊哈哈哈啊哈哈") }
        }, {
            //L.i("===========$it")
        }) {

        }

        viewModel.viewStates.run {
            observeState(this@SplashActivity, MainViewState<BaseResult<List<Data>>>::msg) {
                it?.let {
                    //L.i("==========="+it.toString())
                }
            }
            observeState(this@SplashActivity, MainViewState<BaseResult<List<Data>>>::data) {
                it?.let {
                    value?.fetchStatus?.let { status ->
                        //L.i("==========="+it.data.toString())
                    }
                }
            }
        }

        /*GlobalScope.launch(Dispatchers.IO) {
            val getStr = HttpUtil.get("https://chengshidianliang.net/api/lightup/category/list", json)
            L.i("==========哈哈哈  $getStr")

        }*/

        bind.tv.setOnClickListener {
            /*ARouter.getInstance().build("/test/activity")
                .withString("key3", "888")
                .navigation();*/

            /*val intent = Intent(this, RefreshActivity::class.java)
            intent.putExtra(Tag.UI, IntentData().apply {
                frgParams = JSONObject().apply {
                    put("title", "我是SplashActivity页面传递过来的数据")
                }
            })
            startActivity(intent);*/

            val fragment: BaseFragment<*> = ARouter.getInstance().build("/module/fragment").navigation() as BaseFragment<*>
            var result = object : OnFragmentResult {
                override fun onResult(result: JSONObject) {
                    result?.let {
                        bind.tv.text = result.getString("title")
                        L.i("====你大爷哦哦哦  "+result.toString())
                    }
                }
            }
            openFragment(/*FrgViewPager()*/fragment.apply {
                arguments = Bundle().apply {
                    putSerializable(Tag.FrgData, JSONObject().apply {
                        put("title", "FrgViewPager");
                    })
                }
                mResult = result
            })?.commitAllowingStateLoss();
        };

    }

    private suspend fun getNet(): String {
        GlobalScope.launch {

        }
        return "";
    }

}