/**
 * Copyright 2021 json.cn
 */
package com.base.lib.net.bean;

/**
 * 通用网络回调对象
 */
public class BaseResult<T> {

    public int code;
    public T data;
    public String msg;
    public String error;

    public BaseResult() {

    }

    public BaseResult(int code, T data, String msg, String error) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}

