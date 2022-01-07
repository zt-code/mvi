package com.zt.mvi.demo.refresh

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.base.lib.*
import com.base.lib.base.bus.SingleLiveEvent
import com.base.lib.net.bean.BaseResult
import com.zt.mvi.demo.ApiService
import com.zt.mvi.demo.bean.Data
import com.zt.mvvm.net.Nets
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 4点半修改
 */
class RefreshViewModel(var strType: String) : ViewModel() {

    constructor() : this("我是空哦哦哦")

    private var count: Int = 0
    public var str = "我是你姐姐哦"
    //private val repository: NetRepository = NetRepository.getIns()

    //获取数据状态
    /*private val _dataStates: MutableLiveData<MainViewState<JSONObject>> = MutableLiveData(
        MainViewState()
    )
    val dataStates = _dataStates.asLiveData()*/

    //下拉状态
    private val _viewStates: MutableLiveData<MainViewState<List<Data>>> = MutableLiveData(
        MainViewState()
    )
    val viewStates = _viewStates.asLiveData()

    private val _viewEvents: SingleLiveEvent<MainViewEvent> = SingleLiveEvent() //一次性的事件，与页面状态分开管理
    val viewEvents = _viewEvents.asLiveData()

    fun dispatch(viewAction: MainViewAction) {
        //L.i("==============$strType")
        when (viewAction) {
            is MainViewAction.NewsItemClicked -> newsItemClicked(viewAction.newsItem)
            MainViewAction.OnSmartRefresh -> fetchNews()
            MainViewAction.OnSmartLoadMore -> fetchMore()
            MainViewAction.FabClicked -> fabClicked()
        }
    }

    private fun newsItemClicked(newsItem: Data) {
        _viewEvents.setEvent(MainViewEvent.ClickAdapterItem(newsItem.toString()))
    }

    private fun fabClicked() {
        count++
        _viewEvents.setEvent(MainViewEvent.ShowToast(message = "Fab clicked count $count"))
    }

    private fun fetchNews() {
        _viewStates.setState {
            copy(fetchStatus = RefreshStatus.RefreshIng)
        }

        //GlobalScope
        //viewModelScope
        viewModelScope.launch {
            /*delay(1000)
            val list:List<*> = mutableListOf<Data>() as ArrayList<Data>
            for (index in 0 .. 10) {
                var data = Data();
                data.name = "$index"
                //list.add(data);
            }

            _viewStates.setState { copy(fetchStatus = RefreshStatus.RefreshEnd, list = list) } */

            //Nets.get("aaa").addInterceptor(DemoInterceptor()).build()
            when (val result = Nets.get("aaa").build().create(ApiService::class.java).getLightList().also {  }.await()) {
                is NetState.Success -> {
                    _viewStates.setState { copy(fetchStatus = RefreshStatus.RefreshEnd, data = result.data) }
                }
                is NetState.Error -> {
                    _viewStates.setState { copy(fetchStatus = RefreshStatus.RefreshEnd, msg = result.msg) }
                    _viewEvents.setEvent(MainViewEvent.ShowToast(message = result.msg.toString()))
                }
            }
        }
    }

    private fun fetchMore() {
        _viewStates.setState {
            copy(fetchStatus = RefreshStatus.LoadMoreIng)
        }

        //GlobalScope
        //viewModelScope
        viewModelScope.launch {
            /*delay(1000)
            val list = mutableListOf<Data>()
            for (index in 0 .. 10) {
                var data = Data();
                data.name = "$index"
                list.add(data);
            }

            _viewStates.setState { copy(fetchStatus = RefreshStatus.LoadMoreEnd, list = list) }*/

            Nets.get("aaa").addInterceptor(DemoInterceptor()).build()
            when (val result = Nets.get("aaa").build().create(ApiService::class.java).getLightList().await()) {
                is NetState.Error -> {
                    _viewStates.setState { copy(fetchStatus = RefreshStatus.LoadMoreEnd) }
                    _viewEvents.setEvent(MainViewEvent.ShowToast(message = result.msg.toString()))
                }
                is NetState.Success -> {
                    _viewStates.setState { copy(fetchStatus = RefreshStatus.LoadMoreEnd, data = result.data) }
                }
            }
        }
    }

}