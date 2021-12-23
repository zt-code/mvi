package com.base.lib.net.factory;

import com.base.lib.net.bean.BaseBody;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Objects;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

public class NetStateResponseBodyConverter<T> implements Converter<ResponseBody, BaseBody> {

    private final Type type;
 
    public NetStateResponseBodyConverter(Type type) {
        this.type = type;
    }
 
    /*
    * 转换方法
    */
    @Override
    public BaseBody convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        return new BaseBody(type, tempStr);
        /*if(type.toString().equals("class com.alibaba.fastjson.JSONObject")) {
            return (T) JSON.parseObject(tempStr);
        }else {
            *//*Class cls = getRawType(type);
            L.i("=========="+cls.getTypeName());*//*
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                L.i("=========="+type.getTypeName());
            }
            try{
                return (T) JSON.parseObject(tempStr, type);
            }catch (JSONException e) {
                L.i("==========JSONException");
                return (T) JSON.parseObject(tempStr);
            }
        }*/
    }

    public Class<?> getRawType(Type type) {
        Objects.requireNonNull(type, "type == null");

        if (type instanceof Class<?>) {
            // Type is a normal class.
            return (Class<?>) type;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            // I'm not exactly sure why getRawType() returns Type instead of Class. Neal isn't either but
            // suspects some pathological case related to nested classes exists.
            Type rawType = parameterizedType.getRawType();
            if (!(rawType instanceof Class)) throw new IllegalArgumentException();
            return (Class<?>) rawType;
        }
        if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            // We could use the variable's bounds, but that won't work if there are multiple. Having a raw
            // type that's more general than necessary is okay.
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        }

        throw new IllegalArgumentException(
                "Expected a Class, ParameterizedType, or "
                        + "GenericArrayType, but <"
                        + type
                        + "> is of type "
                        + type.getClass().getName());
    }

}