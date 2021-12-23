package com.zt.mvvm.mvvm

import android.os.Bundle
import android.view.View
import androidx.lifecycle.*
import com.base.lib.base.MIntent
import com.base.lib.base.MyUI
import com.base.lib.base.bus.SingleLiveEvent
import com.base.lib.net.L
import com.base.lib.net.cancel.DeferredS
import com.base.lib.net.cancel.DisposableS
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.lang.ref.WeakReference
import javax.inject.Inject

open class BaseVM @Inject constructor() : IViewModel, Consumer<Disposable> {

    //private var ui: MyUI? = null;

    //弱引用持有
    private var ui: WeakReference<MyUI?>? = null
    private var deferredS: WeakReference<DeferredS?>? = null
    private var disposables: WeakReference<DisposableS?>? = null

    override fun initViewModel(savedInstanceState: Bundle?) {

    }

    fun setMyUI(myUI: MyUI) {
        ui = WeakReference<MyUI?>(myUI);
        ui?.get()?.getLife()?.lifecycle?.addObserver(this)
    }

    fun getMyUI(): MyUI? {
        return ui?.get();
    }

    /*open fun addDeferred(key: String, deferred: Deferred<NetBean<String>>) {
        if (deferredS == null) {
            deferredS = WeakReference<DeferredS?>(DeferredS());
        }
        deferredS?.get()?.remove(key)
        deferredS?.get()?.add(key, deferred)
    }*/

    open fun addDisposable(key: String?, disposable: Disposable?) {
        if (disposables == null) {
            disposables = WeakReference<DisposableS?>(DisposableS());
        }
        disposables?.get()?.remove(key)
        disposables?.get()?.add(key, disposable)
    }

    /************************************************************************************/

    /*override fun onRefresh(tag: Int) {

    }*/

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {

    }

    override fun onCreate() {

    }

    override fun onDestroy() {
        ui?.get()?.getLife()?.lifecycle?.removeObserver(this)
        ui = null
        L.i("====BaseVM onDestroy")
    }

    override fun onStart() {
        //L.i("=======================onStart")
    }

    override fun onStop() {
        //L.i("=======================onStop")
    }

    override fun onResume() {
        //L.i("=======================onResume")
    }

    override fun onPause() {
        //L.i("=======================onPause")
    }

    /*override fun onCleared() {
        super.onCleared()
        //ViewModel销毁时会执行，同时取消所有异步任务
        deferredS?.clear()
        deferredS = null
        disposables?.clear()
        disposables = null
        ui?.get()?.getLife()?.lifecycle?.removeObserver(this)
        ui = null
        L.i("====BaseVM onCleared")
    }*/

    override fun accept(t: Disposable?) {
        addDisposable("ViewModel", t)
    }

}