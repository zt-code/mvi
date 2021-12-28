package com.zt.mvi.demo.refresh

import RefreshStatus
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.activity.viewModels
import com.alibaba.fastjson.JSONObject
import com.base.lib.base.IntentData
import com.base.lib.base.Tag
import com.base.lib.net.L
import com.base.lib.net.bean.BaseResult
import com.base.lib.observeState
import com.base.lib.toast
import com.zt.mvi.*
import com.zt.mvi.databinding.ActivityRefreshBinding
import com.zt.mvi.demo.FrgDemo
import com.zt.mvi.demo.bean.Data
import com.zt.mvvm.base.act.BaseActivity
import com.zt.mvvm.base.act.ContainerActivity
import java.util.*

class RefreshActivity : BaseActivity<ActivityRefreshBinding>() {

    private val viewModel: RefreshViewModel by viewModels() {
        RefreshViewModelFactory(
            "我是你大爷噢噢噢噢噢噢噢噢噢噢噢噢"
        )
    }

    private val newsRvAdapter by lazy {
        DemoAdapter{
            viewModel.dispatch(MainViewAction.NewsItemClicked(it.tag as Data))
        }
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_refresh;
    }

    override fun initView(savedInstanceState: Bundle?) {

        bind.rvNewsHome.adapter = newsRvAdapter

        bind.srlNewsHome.setOnRefreshListener {
            viewModel.dispatch(MainViewAction.OnSmartRefresh)
        }

        bind.srlNewsHome.setOnLoadMoreListener {
            viewModel.dispatch(MainViewAction.OnSmartLoadMore)
        }

        bind.fabStar.setOnClickListener {
            viewModel.dispatch(MainViewAction.FabClicked)
        }

        initViewModel()
    }


    private fun initViewModel() {
        viewModel.viewStates.run {

            observeState(this@RefreshActivity, MainViewState<List<Data>>::data, MainViewState<List<Data>>::fetchStatus) { data, status->
                if(status == RefreshStatus.RefreshEnd) {
                    newsRvAdapter.submitList(data)
                }else if(status == RefreshStatus.LoadMoreEnd) {
                    val newList = mutableListOf<Data>()
                    var oldList = newsRvAdapter.currentList
                    newList.addAll(oldList);
                    data?.let {
                        newList.addAll(it);
                    }
                    newsRvAdapter.submitList(newList);
                }
                when (status) {
                    is RefreshStatus.NotRefresh -> bind.srlNewsHome.autoRefresh()
                    is RefreshStatus.RefreshEnd -> bind.srlNewsHome.finishRefresh()
                    is RefreshStatus.LoadMoreEnd -> bind.srlNewsHome.finishLoadMore()
                    else -> bind.srlNewsHome.autoRefresh()
                }
            }


            observeState(this@RefreshActivity, MainViewState<List<Data>>::msg) {
                it?.let {

                }
            }

            /*observeState(this@RefreshActivity, MainViewState<List<Data>>::data) {
                it?.let {
                    value?.fetchStatus?.let { status ->
                        //L.i("==========="+it.toString())
                        newsRvAdapter.submitList(it)

                        *//*if(status == RefreshStatus.RefreshEnd) {
                            newsRvAdapter.submitList(it.data)
                        }else if(status == RefreshStatus.LoadMoreEnd) {
                            val newList = mutableListOf<Data>()
                            var oldList = newsRvAdapter.currentList
                            newList.addAll(oldList);
                            newList.addAll(it.data);
                            newsRvAdapter.submitList(newList);
                        }*//*
                    }
                }
            }*/


            /*observeState(this@RefreshActivity, MainViewState<List<Data>>::fetchStatus) {
                when (it) {
                    is RefreshStatus.NotRefresh -> bind.srlNewsHome.autoRefresh()
                    is RefreshStatus.RefreshEnd -> bind.srlNewsHome.finishRefresh()
                    is RefreshStatus.LoadMoreEnd -> bind.srlNewsHome.finishLoadMore()
                    else -> bind.srlNewsHome.autoRefresh()
                }
            }*/
        }

        viewModel.viewEvents.observe(this) {
            renderViewEvent(it)
        }
    }

    private fun renderViewEvent(viewEvent: MainViewEvent) {
        when (viewEvent) {
            is MainViewEvent.ShowSnackbar -> Snackbar.make(bind.coordinatorLayoutRoot, viewEvent.message, Snackbar.LENGTH_SHORT).show()
            is MainViewEvent.ShowToast -> toast(message = viewEvent.message)
            is MainViewEvent.ClickAdapterItem -> {
                val intent = Intent(this, ContainerActivity::class.java)
                intent.putExtra(Tag.UI, IntentData().apply {
                    frg = FrgDemo::class.java
                    frgParams = JSONObject().apply {
                        put("title", "我是你大爷")
                    }
                })
                startActivity(intent);
            }
        }
    }

}