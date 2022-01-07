package com.base.lib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;

/**
 * Created by goldze on 2017/5/14.
 * 常用工具类
 */
public final class Utils {

    private static Context context;

    public static void init(@NonNull final Context context) {
        Utils.context = context.getApplicationContext();
    }

    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("should be initialized in application");
    }

}