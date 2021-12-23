package com.base.lib.net.bean

import java.lang.reflect.Type

/**
 * str 存放网络接口返回的String格式数据
 * type 存放接下来准备实例化的对象类型"T=JSON.parseObject(str, type)"
 */
data class BaseBody(var type: Type, var str: String) {

}