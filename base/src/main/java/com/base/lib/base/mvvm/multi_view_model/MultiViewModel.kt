package com.zt.mvvm.mvvm.multi_view_model

import com.zt.mvvm.mvvm.BaseVM

open class MultiViewModel<VM : BaseVM>(viewModel: VM) : BaseMultiViewModel<VM>(viewModel) {

    var itemType: String? = null
        protected set

    fun multiItemType(multiType: String) {
        itemType = multiType
    }

}