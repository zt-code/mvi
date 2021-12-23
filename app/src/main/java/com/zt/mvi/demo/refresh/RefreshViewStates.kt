package com.zt.mvi.demo.refresh

import RefreshStatus
import com.alibaba.fastjson.JSONObject
import com.zt.mvi.demo.bean.Data

data class MainViewState<T>(
    val fetchStatus: RefreshStatus = RefreshStatus.NotRefresh,
    val data: T? = null,
    val msg: JSONObject? = null,
)

sealed class MainViewEvent {
    data class ClickAdapterItem(val message: String) : MainViewEvent()
    data class ShowSnackbar(val message: String) : MainViewEvent()
    data class ShowToast(val message: String) : MainViewEvent()
}

sealed class MainViewAction {
    data class NewsItemClicked(val newsItem: Data) : MainViewAction()
    object OnSmartRefresh : MainViewAction()
    object OnSmartLoadMore : MainViewAction()
    object FabClicked : MainViewAction()
}