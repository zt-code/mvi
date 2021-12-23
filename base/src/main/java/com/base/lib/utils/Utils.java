package com.base.lib.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Created by goldze on 2017/5/14.
 * 常用工具类
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static Class<?> cls;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(@NonNull final Context context) {
        Utils.context = context.getApplicationContext();
    }

    public static void initContainerAct(@NonNull final Class<?> cls) {
        Utils.cls = cls;
    }

    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("should be initialized in application");
    }

    public static Class<?> getContainerAct() {
        if (cls != null) {
            return cls;
        }
        throw new NullPointerException("should be initialized in application");
    }

}