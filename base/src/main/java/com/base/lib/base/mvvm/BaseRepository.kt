package com.zt.mvvm.mvvm

import com.base.lib.net.L
import javax.inject.Inject

/**
 * MVVM的Model层，统一模块的数据仓库，包含网络数据和本地数据（一个应用可以有多个Repositor）
 * Created by goldze on 2019/3/26.
 */
open class BaseRepository @Inject constructor() : IRepository {

    override fun onCleared() {

    }

    fun baseRepositoryDoSome(str: String) {
        L.i("====BaseRepository  doSome $str")
    }

}