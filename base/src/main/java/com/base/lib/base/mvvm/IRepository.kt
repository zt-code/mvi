package com.zt.mvvm.mvvm

/**
 * Created by goldze on 2017/6/15.
 */
interface IRepository {
    /**
     * ViewModel销毁时清除Model，与ViewModel共消亡。Model层同样不能持有长生命周期对象
     */
    fun onCleared()
}