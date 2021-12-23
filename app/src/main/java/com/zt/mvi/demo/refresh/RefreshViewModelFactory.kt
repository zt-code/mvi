package com.zt.mvi.demo.refresh

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.base.lib.base.Tag
import com.base.lib.base.mvvm.ViewModels
import com.base.lib.net.L

class RefreshViewModelFactory(
        private val type: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RefreshViewModel::class.java)) {
                L.i("初始化 RefreshViewModel")
                @Suppress("UNCHECKED_CAST")
                return RefreshViewModel(type) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }