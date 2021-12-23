package com.base.lib

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.base.lib.base.bus.SingleLiveEvent
import com.base.lib.base.bus.SingleLiveEvents
import kotlin.reflect.KProperty1
import kotlin.reflect.KProperty2



fun <T, A> LiveData<T>.observeState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<T, A>,
    action: (A) -> Unit
) {
    this.map {
        StateTuple1(prop1.get(it))
    }.distinctUntilChanged().observe(lifecycleOwner, Observer {
        action.invoke(it.a)
    })
    /*this.map {
        StateTuple1(prop1.get(it))
    }.distinctUntilChanged().observe(lifecycleOwner) { (a) ->
        action.invoke(a)
    }*/
}

fun <T, A, B> LiveData<T>.observeState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<T, A>,
    prop2: KProperty1<T, B>,
    action: (A, B) -> Unit
) {
    this.map {
        StateTuple2(prop1.get(it), prop2.get(it))
    }.distinctUntilChanged().observe(lifecycleOwner, Observer {
        action.invoke(it.a, it.b)
    })
    /*this.map {
        StateTuple2(prop1.get(it), prop2.get(it))
    }.distinctUntilChanged().observe(lifecycleOwner) { (a, b) ->
        action.invoke(a, b)
    }*/
}

fun <T, A, B, C> LiveData<T>.observeState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<T, A>,
    prop2: KProperty1<T, B>,
    prop3: KProperty1<T, C>,
    action: (A, B, C) -> Unit
) {
    this.map {
        StateTuple3(prop1.get(it), prop2.get(it), prop3.get(it))
    }.distinctUntilChanged().observe(lifecycleOwner, Observer {
        action.invoke(it.a, it.b, it.c)
    })
    /*this.map {
        StateTuple3(prop1.get(it), prop2.get(it), prop3.get(it))
    }.distinctUntilChanged().observe(lifecycleOwner) { (a, b, c) ->
        action.invoke(a, b, c)
    }*/
}

internal data class StateTuple1<A>(val a: A)
internal data class StateTuple2<A, B>(val a: A, val b: B)
internal data class StateTuple3<A, B, C>(val a: A, val b: B, val c: C)

fun <T> MutableLiveData<T>.setState(reducer: T.() -> T) {
    this.value = this.value?.reducer()
}

fun <T> SingleLiveEvent<T>.setEvent(value: T) {
    this.value = value
}

fun <T> SingleLiveEvents<T>.setEvent(vararg values: T) {
    this.value = values.toList()
}

fun <T> LiveData<List<T>>.observeEvent(lifecycleOwner: LifecycleOwner, action: (T) -> Unit) {
    this.observe(lifecycleOwner, Observer {
        it.forEach { event ->
            action.invoke(event)
        }
    })
    /*this.observe(lifecycleOwner) {
        it.forEach { event ->
            action.invoke(event)
        }
    }*/
}

fun <T, R> withState(state: LiveData<T>, block: (T) -> R): R? {
    return state.value?.let(block)
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}

fun CharSequence?.toList(): List<JSONObject> {
    return this?.let {
        val list: List<JSONObject>? = JSON.parseArray(it.toString()) as? List<JSONObject>;
        return list?:emptyList();
    }?: emptyList();
}

fun CharSequence?.toJson(): JSONObject {
    return this?.let {
        val json: JSONObject? = JSON.parseObject(it.toString());
        return json ?: JSONObject();
    } ?: JSONObject();
}
