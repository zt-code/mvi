package com.base.lib.base.bus

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
* 一个生命周期感知的可观察对象，它只在订阅后发送新的更新，用于以下事件
* Toast和Snackbar信息;在我们的例子中是vieweeffect。
*
* < p >
* 这避免了一个常见的事件问题:在配置更改(如旋转)时更新
* 在观察者处于活动状态时可以被触发。这个LiveData只会调用可观察对象如果有
* 显式调用setValue()或call()。
* < p >
*
* 注意:只有一个观察者会被通知的变化，没有保证是哪一个。
*
* @see <a href="https://github.com/android/architecture-samples/blob/dev-todo-mvvm-live/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/SingleLiveEvent.java">google-architecture-samples</a>
*
*/

open class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }
}