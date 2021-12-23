package com.zt.common.bean

import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class User @Inject constructor(/*var int: Int = 0, var str: String = ""*/) {

    var str: String = "";
    var num: Int = 0;

}
