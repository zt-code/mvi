package com.base.lib.utils.down;

import android.os.Handler;
import android.os.SystemClock;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownHelper {

    public interface DownListener {
        void onPrepare();
        void onWrite(long num);
        void onProgress(long progress);
        void onEnd();
        void onError();
    }

    public static String d = "✄";
    private static DownHelper helper;
    public static DownHelper getIns() {
        if (helper == null) {
            synchronized (DownHelper.class) {
                if (helper == null) {
                    helper = new DownHelper();
                }
            }
        }
        return helper;
    }

    public void down(File file, String mUrl, DownListener listener) {
        RandomAccessFile accessFile = null;
        HttpURLConnection http = null;
        InputStream inStream = null;
        try {
            String realUrl = getRedirectionUrl(mUrl);
            URL sizeUrl = new URL(realUrl);
            HttpURLConnection sizeHttp = (HttpURLConnection)sizeUrl.openConnection();
            sizeHttp.setRequestMethod("GET");
            sizeHttp.connect();

            long end = sizeHttp.getContentLength();
            sizeHttp.disconnect();

            if (end <= 0){
                L.i("why", "文件大小 = " + end + "\t, 终止下载过程");
                listener.onError();
                return;
            }

            //准备下载
            listener.onPrepare();

            if(file.length() == end) {
                if(file.exists()) {
                    //下载完成
                    listener.onEnd();
                    return;
                }
            }

            accessFile = new RandomAccessFile(file, "rwd");

            URL url = new URL(realUrl);
            http = (HttpURLConnection)url.openConnection();
            http.setConnectTimeout(10000);
            http.setRequestProperty("Connection", "Keep-Alive");
            http.setReadTimeout(10000);
            http.setRequestProperty("Range", "bytes=" + file.length() + "-");
            http.connect();

            inStream = http.getInputStream();
            byte[] buffer = new byte[1024*8];
            int offset;

            accessFile.seek(file.length());
            long millis = SystemClock.uptimeMillis();
            long start = file.length();
            while ((offset = inStream.read(buffer)) != -1){
                /*if (isPause){
                    L.i("down哈哈",  "下载过程 设置了 暂停");
                    return;
                }*/
                accessFile.write(buffer,0, offset);
                start = start + offset;

                listener.onWrite(start);
                L.i("why", " 下载进度 = " + start);

                if (SystemClock.uptimeMillis()-millis >= 1000){
                    millis = SystemClock.uptimeMillis();
                    listener.onProgress(start);
                    L.i("why", " 下载进度 发送msg = " + start);
                }
            } // end of "while(..."

            listener.onEnd();
            L.i("why", " 下载完成 = " + start);

        } catch (Exception e){
            listener.onError();
            L.i("why",  "下载过程发生失败");
        } finally {
            try {
                if (accessFile != null){
                    accessFile.close();
                }
                if (inStream != null){
                    inStream.close();
                }
                if (http != null){
                    http.disconnect();
                }
            }catch (IOException e){
                L.i("why",  "finally 块 关闭文件过程中 发生异常");
            }
        }
    }

    public void down(File file, String mUrl, Handler handler) {
        RandomAccessFile accessFile = null;
        HttpURLConnection http = null;
        InputStream inStream = null;
        try {
            String realUrl = getRedirectionUrl(mUrl);
            URL sizeUrl = new URL(realUrl);
            HttpURLConnection sizeHttp = (HttpURLConnection)sizeUrl.openConnection();
            sizeHttp.setRequestMethod("GET");
            sizeHttp.connect();

            long end = sizeHttp.getContentLength();
            sizeHttp.disconnect();

            if (end <= 0){
                L.i("why", "文件大小 = " + end + "\t, 终止下载过程");
                return;
            }

            /*DownInfo info = DownManager.getIns().getByName(file.getName());
            if(info == null) {
                info = new DownInfo();
                info.tag = 0;
                info.name = file.getName();
                info.path = file.getAbsolutePath();
                info.start = file.length();
                info.end = end;
                DownManager.getIns().insert(info);
            } else {
                info.end = end;
                DownManager.getIns().update(info);
            }*/

            if(file.length() == end) {
                if(file.exists()) {
                    /*info.tag = 2;
                    DownManager.getIns().update(info);*/
                    handler.sendEmptyMessage(0);
                    return;
                }
            }

            accessFile = new RandomAccessFile(file, "rwd");

            URL url = new URL(realUrl);
            http = (HttpURLConnection)url.openConnection();
            http.setConnectTimeout(10000);
            http.setRequestProperty("Connection", "Keep-Alive");
            http.setReadTimeout(10000);
            http.setRequestProperty("Range", "bytes=" + file.length() + "-");
            http.connect();

            inStream = http.getInputStream();
            byte[] buffer = new byte[1024*8];
            int offset;

            accessFile.seek(file.length());
            long millis = SystemClock.uptimeMillis();
            long start = file.length();
            while ((offset = inStream.read(buffer)) != -1){
                /*if (isPause){
                    L.i("down哈哈",  "下载过程 设置了 暂停");
                    return;
                }*/
                accessFile.write(buffer,0, offset);
                start = start + offset;
                L.i("why", " 下载进度 = " + start);

                //DownManager.getIns().update(info);
                if (SystemClock.uptimeMillis()-millis >= 1000){
                    millis = SystemClock.uptimeMillis();
                    handler.sendEmptyMessage(0);
                    L.i("why", " 下载进度 发送msg = " + start);
                }
            } // end of "while(..."

            /*info.tag = 2;
            DownManager.getIns().update(info);*/
            handler.sendEmptyMessageDelayed(0, 100);
            L.i("why", " 下载完成 = " + start);

        } catch (Exception e){
            L.i("why",  "下载过程发生失败");
            handler.sendEmptyMessageDelayed(1, 100);
        } finally {
            try {
                if (accessFile != null){
                    accessFile.close();
                }
                if (inStream != null){
                    inStream.close();
                }
                if (http != null){
                    http.disconnect();
                }
            }catch (IOException e){
                L.i("why",  "finally 块  关闭文件过程中 发生异常");
            }
        }
    }

    private String getRedirectionUrl(String sourceUrl){
        String redirUrl = sourceUrl;
        try {
            URL url = new URL(sourceUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode == 302){
                redirUrl = conn.getHeaderField("Location");
                L.i("why", " 下载地址重定向为 = " + redirUrl);
            }
            conn.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
        return redirUrl;
    }

}
