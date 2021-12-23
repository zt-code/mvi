package com.zt.mvvm.mvvm.multi_view_model

import com.zt.mvvm.mvvm.BaseVM

/**
 * BaseMultiViewModel
 * Created by goldze on 2018/10/3.
 */
open class BaseMultiViewModel<VM : BaseVM?>(protected var viewModel: VM)