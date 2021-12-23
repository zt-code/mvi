package com.zt.mvi.demo.bean

import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class IntTag @Inject constructor() {
    var num = 0;
}