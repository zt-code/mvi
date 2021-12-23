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
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import androidx.core.content.FileProvider;
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
 * 
 * @author zhy
 * 
 */
public class AppUtils {

	//获取应用程序名称
	public static String getAppName(Context ctx) {
		String applicationName = "";
		try {
			PackageManager packageManager = ctx.getPackageManager();
			ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), 0);
			applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
		} catch (NameNotFoundException e) {
		}
		return applicationName;
	}

	//获取应用程序图标
	public static Drawable getApplicationIcon(Context ctx) {
		Drawable icon = null;
		try {
			PackageManager packageManager = ctx.getPackageManager();
			ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), 0);
			icon = packageManager.getApplicationIcon(applicationInfo);
		} catch (NameNotFoundException e) {
		}
		return icon;
	}


	public static String getVersionName() {
		return getVersionName(Utils.getContext());
	}

	//当前应用的版本名称
	public static String getVersionName(Context ctx) {
		try {
			PackageManager packageManager = ctx.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					ctx.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	//当前应用的版本号
	public static String getVersionCode(Context ctx) {
		try {
			PackageManager packageManager = ctx.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					ctx.getPackageName(), 0);
			return packageInfo.versionCode+"";
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return 0+"";
	}

	//获取AndroidManifest节点内容
	public static String getMetaData(String tag) {
		String meta = "";
		try {
			ApplicationInfo appInfo = Utils.getContext().getPackageManager().getApplicationInfo(Utils.getContext().getPackageName(), PackageManager.GET_META_DATA);
			meta = appInfo.metaData.getString(tag);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return meta;
	}

	//App是否进入后台
	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	//获取屏幕分辨率
	public static String getResolution(Context ctx) {
		WindowManager wm = (WindowManager) ctx.getSystemService(ctx.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);
		int width = point.x;
		int height = point.y;
		String resolution = width + "x" +height;
		return resolution;
	}

	/**
	 * 获得屏幕宽度
	 * @return
	 */
	public static int getScreenWidth(Context ctx) {
		WindowManager wm = (WindowManager) ctx.getSystemService(ctx.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * @return 获取屏幕内容高度不包括虚拟按键
	 */
	public static int getScreenHeight(Context ctx) {
		WindowManager wm = (WindowManager) ctx
				.getSystemService(ctx.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**
	 * @return 获取屏幕原始尺寸高度，包括虚拟功能键高度
	 */
	public static int getTotalHeight(Context ctx) {
		int dpi = 0;
		WindowManager windowManager = (WindowManager) ctx.getSystemService(ctx.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		@SuppressWarnings("rawtypes")
        Class c;
		try {
			c = Class.forName("android.view.Display");
			@SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
			method.invoke(display, displayMetrics);
			dpi = displayMetrics.heightPixels;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dpi;
	}

	/**
	 * 获得顶部状态栏的高度
	 * @return
	 */
	public static int getHeaderBarHeight(Context ctx) {
		Resources resources = ctx.getResources();
		int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
		int height = resources.getDimensionPixelSize(resourceId);
		return height;
	}

	/**
	 * 获得底部状态栏的高度
	 * @return
	 */
	public static float getFooterBarHeight(Context ctx) {
		if(!checkDeviceHasNavigationBar(ctx)) return 0;
		return getTotalHeight(ctx) - getScreenHeight(ctx);
	}

	/**
	 * 获得底部状态栏的高度
	 * @return
	 */
	public static int getNavigationBarHeight(Context context) {
		if(!checkDeviceHasNavigationBar(context)) return 0;
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
		int height = resources.getDimensionPixelSize(resourceId);
		return height;
	}

	//获取是否存在NavigationBar
	public static boolean checkDeviceHasNavigationBar(Context context) {
		boolean hasNavigationBar = false;
		Resources rs = context.getResources();
		int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
		if (id > 0) {
			hasNavigationBar = rs.getBoolean(id);
		}
		try {
			Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
			Method m = systemPropertiesClass.getMethod("get", String.class);
			String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
			if ("1".equals(navBarOverride)) {
				hasNavigationBar = false;
			} else if ("0".equals(navBarOverride)) {
				hasNavigationBar = true;
			}
		} catch (Exception e) {

		}
		return hasNavigationBar;

	}

	//获取屏幕密度（displayMetrics.density）
	public static DisplayMetrics getDensity(Context ctx) {
		DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
		return displayMetrics;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = (int) getScreenWidth(activity);
		int height = (int) getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;
	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		int width = (int) getScreenWidth(activity);
		int height = (int) getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;
	}

	/**
	 * 获取MAC地址
	 * @param context
	 * @return
	 */
	public static String getMacAddress(Context context) {
		String mac = "02:00:00:00:00:00";
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			mac = getMacDefault(context);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
			mac = getMacFromFile();
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			mac = getMacFromHardware();
		}
		return mac;
	}

	/**
	 * Android  6.0 之前（不包括6.0）
	 * 必须的权限  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
	 * @param context
	 * @return
	 */
	@SuppressLint("MissingPermission")
	private static String getMacDefault(Context context) {
		String mac = "02:00:00:00:00:00";
		if (context == null) {
			return mac;
		}
		WifiManager wifi = (WifiManager) context.getApplicationContext()
				.getSystemService(Context.WIFI_SERVICE);
		if (wifi == null) {
			return mac;
		}
		WifiInfo info = null;
		try {
			info = wifi.getConnectionInfo();
		} catch (Exception e) {
		}
		if (info == null) {
			return null;
		}
		mac = info.getMacAddress();
		if (!TextUtils.isEmpty(mac)) {
			mac = mac.toUpperCase(Locale.ENGLISH);
		}
		return mac;
	}

	/**
	 * Android 6.0（包括） - Android 7.0（不包括）
	 * @return
	 */
	private static String getMacFromFile() {
		String WifiAddress = "02:00:00:00:00:00";
		try {
			WifiAddress = new BufferedReader(new FileReader(new File("/sys/class/net/wlan0/address"))).readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return WifiAddress;
	}

	/**
	 * 遍历循环所有的网络接口，找到接口是 wlan0
	 * 必须的权限 <uses-permission android:name="android.permission.INTERNET" />
	 * @return
	 */
	private static String getMacFromHardware() {
		try {
			List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface nif : all) {
				if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
				byte[] macBytes = nif.getHardwareAddress();
				if (macBytes == null) {
					return "";
				}
				StringBuilder res1 = new StringBuilder();
				for (byte b : macBytes) {
					res1.append(String.format("%02X:", b));
				}
				if (res1.length() > 0) {
					res1.deleteCharAt(res1.length() - 1);
				}
				return res1.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "02:00:00:00:00:00";
	}

	/**
	 * 获取android当前可用内存大小
	 * ram使用情况
	 * @return
	 */
	public static String getAvailMemory(Context ctx) {// 获取android当前可用内存大小
		ActivityManager am = (ActivityManager)ctx.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(mi);
		//mi.availMem; 当前系统的可用内存
		return Formatter.formatFileSize(ctx, mi.availMem);// 将获取的内存大小规格化
	}

	//Rom使用情况
	public static String getRomMemroy(Context ctx) {
		long romInfo;
		//Available rom memory
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		romInfo = blockSize * availableBlocks;
		return Formatter.formatFileSize(ctx, romInfo);
	}

	public static String getUserAgent() {
		String userAgent = "";
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			try {
				userAgent = WebSettings.getDefaultUserAgent(Utils.getContext());
			} catch (Exception e) {
				userAgent = System.getProperty("http.agent");
			}
		} else {
			userAgent = System.getProperty("http.agent");
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0, length = userAgent.length(); i < length; i++) {
			char c = userAgent.charAt(i);
			if (c <= '\u001f' || c >= '\u007f') {
				sb.append(String.format("\\u%04x", (int) c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	//获取当前的运营商名字
	/*public static String getSimOperatorName() {
		String carrier = "";
		String IMSI = getIMSI();
		if (IMSI != null) {
			if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
				carrier = "chinamobile";
			} else if (IMSI.startsWith("46001")  || IMSI.startsWith("46006")) {
				carrier = "chinaunicom";
			} else if (IMSI.startsWith("46003")) {
				carrier = "chinatelecom";
			}
		}
		return carrier;
	}*/

	//移动sim运营商类型
	/*public static String getSimOperatorType() {
		String imsi = getIMSI();
		if (imsi != null) {
			if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007")) {
				// 中国移动
				return "0";
			} else if (imsi.startsWith("46001") || imsi.startsWith("46006")) {
				// 中国联通
				return "1";
			} else if (imsi.startsWith("46003") || imsi.startsWith("46005") || imsi.startsWith("46011")) {
				// 中国电信
				return "2";
			}
		}
		return "0";
	}*/

	//移动sim运营商类型
	/*public static String getSimOperatorNum() {
		String imsi = getIMSI();
		if (imsi != null) {
			if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007")) {
				// 中国移动
				return "46000";
			} else if (imsi.startsWith("46001") || imsi.startsWith("46006")) {
				// 中国联通
				return "46001";
			} else if (imsi.startsWith("46003") || imsi.startsWith("46005") || imsi.startsWith("46011")) {
				// 中国电信
				return "46003";
			}
		}
		return "0";
	}*/

	//手机系统版本
	public static String getOsVersion() {
		return Build.VERSION.RELEASE;
	}

	//制造商
	public static String getManufacture() {
		return Build.MANUFACTURER;
	}

	//主板
	public static String getMotherboard() {
		return Build.BOARD;
	}

	//设备品牌
	public static String getBrand() {
		return Build.MODEL;
	}

	//设备名称
	public static String getDeviceName() {
		return Build.PRODUCT;
	}

	//硬件名称
	public static String getHardwareName() {
		return Build.HARDWARE;
	}

	//硬件id
	public static String getHardwareId() {
		return Build.SERIAL;
	}


	//出厂时间
	public static String getManufactureDate() {
		return String.valueOf(Build.TIME);
	}


	//SDK版本号
	public static String getSdkVersion() {
		return String.valueOf(Build.VERSION.SDK_INT);
	}


	//系统版本号
	public static String getSystemVersion() {
		return Build.VERSION.RELEASE;
	}

	//源码控制版本号
	public static String getsourceCodeVersion() {
		return Build.VERSION.INCREMENTAL;
	}

	/**
	 * 安装APP
	 * @param context
	 * @param apkFile
	 */
	public static void installApk(Context context, File apkFile) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		try {
			String[] command = {"chmod", "777", apkFile.toString()};
			ProcessBuilder builder = new ProcessBuilder(command);
			builder.start();
		} catch (IOException ignored) {
			ignored.printStackTrace();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			/*L.i( "=============installApk  "+context.getPackageName() + ".lightHxxProvider");
			L.i( "=============installApk  "+apkFile.getAbsolutePath());*/
			//做区分
			Uri contentUri = FileProvider.getUriForFile(context.getApplicationContext(), context.getPackageName() + ".lightHxxProvider", apkFile);
			intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
		} else {
			intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		context.startActivity(intent);
	}

	/*
	 * 通过本地磁盘保存32位随机数，作为用户唯一识别码(极小几率重复)，用户删除APP或清理缓存会失效
	 * */
	public static String getDeviceID() {
		String id = "";
		if(ACache.get().getAsString("DEVICE_ID") == null) {
			id = getbizId(32);
			ACache.get().put("DEVICE_ID", id);
			//L.i( "第一次  "+id);
		}else {
			id =  ACache.get().getAsString("DEVICE_ID");
			//L.i( "第二次  "+id);
		}
		return id;
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

	/***
	 * 获取url指定name的value;
	 * @param url
	 * @param name
	 * @return
	 */
	public static String getValueByName(String url, String name) {
		String result = "";
		int index = url.indexOf("?");
		if(index != -1){
			String temp = url.substring(index + 1);
			String[] keyValue = temp.split("&");
			if(keyValue != null){
				for (String str : keyValue) {
					if(str == null || str.length() == 0){
						continue;
					}
					if (str.contains(name)) {
						result = str.replace(name + "=", "");
						break;
					}
				}
			}
		}
		return result;
	}

	/**
	 * 转换文件大小
	 * @param fileS
	 * @return
	 */
	public static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("0.00");
		String fileSizeString = "";
		String wrongSize = "0B";
		if (fileS == 0) {
			return wrongSize;
		}
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "GB";
		}
		return fileSizeString;
	}

	public static String formatNum(long coin){
		String txt = "";
		DecimalFormat df = new DecimalFormat("0.00");
		if(coin > 100000000){
			double num = (double) coin / 100000000;
			BigDecimal bd = new BigDecimal(num);
			double result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			txt = result + "亿";
		} else if(coin > 10000){
			double num = (double) coin / 10000;
			BigDecimal bd = new BigDecimal(num);
			double result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			txt = result + "万";
		} else {
			txt = String.valueOf(coin);
		}
		return txt;
	}

	public static void copyStrToClipboard(Context context, String content) {
		ClipData clipData = ClipData.newPlainText("text", content);
		ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		manager.setPrimaryClip(clipData);
	}

	public static int getResId(Context context, String name, String type){
		int id = context.getResources().getIdentifier(name, type, context.getPackageName());
		return id;
	}

	public static void addContact(Context context, String name, String phoneNumber) {
		// 创建一个空的ContentValues
		ContentValues values = new ContentValues();
		// 向RawContacts.CONTENT_URI空值插入，
		// 先获取Android系统返回的rawContactId
		// 后面要基于此id插入值
		Uri rawContactUri = context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
		long rawContactId = ContentUris.parseId(rawContactUri);
		values.clear();
		// 添加id
		values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
		// 内容类型
		values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
		// 联系人名字
		values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
		// 向联系人URI添加联系人名字
		context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
		values.clear();

		values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
		values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
		// 联系人的电话号码
		values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);
		// 电话类型
		values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
		// 向联系人电话号码URI添加电话号码
		context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
		values.clear();

		//以下为插入e-mail信息，不需要可以注释掉
        /*values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Email.DATA, "zhangphil@xxx.com");  // 联系人的Email地址
        values.put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);    // 电子邮件的类型
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values); // 向联系人Email URI添加Email数据
		Toast.makeText(context, "联系人数据添加成功", Toast.LENGTH_SHORT).show();*/
	}

	/**
	 * 版本号比较
	 *
	 * @param version1
	 * @param version2
	 * @return
	 */
	public static int compareVersion(String version1, String version2) {
		if (version1.equals(version2)) {
			return 0;
		}
		String[] version1Array = version1.split("\\.");
		String[] version2Array = version2.split("\\.");
		int index = 0;
		// 获取最小长度值
		int minLen = Math.min(version1Array.length, version2Array.length);
		int diff = 0;
		// 循环判断每位的大小
		while (index < minLen
				&& (diff = Integer.parseInt(version1Array[index])
				- Integer.parseInt(version2Array[index])) == 0) {
			index++;
		}
		if (diff == 0) {
			// 如果位数不一致，比较多余位数
			for (int i = index; i < version1Array.length; i++) {
				if (Integer.parseInt(version1Array[i]) > 0) {
					return 1;
				}
			}

			for (int i = index; i < version2Array.length; i++) {
				if (Integer.parseInt(version2Array[i]) > 0) {
					return -1;
				}
			}
			return 0;
		} else {
			return diff > 0 ? 1 : -1;
		}
	}

}
