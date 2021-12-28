package com.base.lib.utils.down;

import android.util.Log;

/**
 * Log统一管理类
 * 
 * @author way
 * 
 */
public class L
{

	private L()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
	private static final String TAG = "why";

	// 下面四个是默认tag的函数
	public static void i(String msg)
	{
		if (isDebug) {
			if(msg.length() > 4000) {
				for(int j=0;j<msg.length();j+=4000){
					if(j+4000<msg.length()) {
						L.i(TAG, msg.substring(j, j+4000));
					} else {
						L.i(TAG, msg.substring(j, msg.length()));
					}
				}
			} else {
				L.i(TAG, msg);
			}
		}
	}


	public static void d(String msg)
	{
		if (isDebug)
			Log.d(TAG, msg);
	}

	public static void e(String msg)
	{
		if (isDebug)
			Log.e(TAG, msg);
	}

	public static void v(String msg)
	{
		if (isDebug)
			Log.v(TAG, msg);
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg)
	{
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg)
	{
		if (isDebug)
			Log.d(tag, msg);
	}

	public static void e(String tag, String msg)
	{
		if (isDebug)
			Log.e(tag, msg);
	}

	public static void v(String tag, String msg)
	{
		if (isDebug)
			Log.v(tag, msg);
	}

}