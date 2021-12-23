package com.base.lib.base

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.alibaba.fastjson.JSONObject

interface MyUI {
    fun getLife(): LifecycleOwner?
    fun getUIManager(): FragmentManager?
    fun getData(): JSONObject
}