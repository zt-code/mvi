package com.base.lib.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.base.lib.net.L;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;


/**
 * 跟App相关的辅助类
 * @author zt
 * 
 */
public class AppUtil {

    private static Context ctx;

    public static void init(@NonNull final Context context) {
        AppUtil.ctx = context.getApplicationContext();
    }

    public static Context getContext() {
        return ctx;
    }

    /*
     * 通过本地磁盘保存32位随机数，作为用户唯一识别码(极小几率重复)，用户删除APP或清理缓存会失效
     * */
    public static String getDeviceID() {
        String serial = getSerial();
//		String android_id = getAndroidId(ctx);
        boolean tag1 = serial.length()>=16;
//		boolean tag2 = android_id.length()>=16;

        if(tag1) return serial;
//		if(tag2) return android_id;
        StringBuffer sb = new StringBuffer();
        sb.append(SystemClock.elapsedRealtime());
        sb.append(getbizId(32));
        sb.append(System.currentTimeMillis());
        String txt = sb.toString();
        L.i("uuoo", "保存 uuid =  "+txt);
        return txt;
    }

    /**
     * 获取设备序列号
     * @return
     */
    public static String getSerial() {
        String serial = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                serial = Build.getSerial();
            } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                serial = Build.SERIAL;
            } else {
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class);
                serial = (String) get.invoke(c, "ro.serialno");
            }
        } catch (Exception e) {
            L.d("serial->exp:" + e.getMessage() + "    serial  "+serial);
        }

        if(serial == null) serial = "";
        int length = serial.length();
        if(length >= 16 && length < 32) {
            serial = serial + getbizId(32-length);
            L.i("uuoo", "保存 deviceId = serial  "+serial);
        }
        return serial;
    }

    public static String getAndroidId(Context paramContext) {
        String ANDROID_ID = "";
        if (Build.VERSION.SDK_INT >= 26) {
            ANDROID_ID = Settings.System.getString(paramContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            ANDROID_ID = Settings.System.getString(paramContext.getContentResolver(), Settings.System.ANDROID_ID);
        }
        if(ANDROID_ID == null) ANDROID_ID = "";
        int length = ANDROID_ID.length();
        if(length >= 16 && length < 32) {
            ANDROID_ID = ANDROID_ID + getbizId(32-length);
            L.i("uuoo", "保存 deviceId = android_id  "+ANDROID_ID);
        }
        return ANDROID_ID;
    }

    public static String getbizId(int length){
        String str = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
