package com.base.lib.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.base.lib.R;

public class ToastUtil {

    private static ToastUtil ins;

    private ToastUtil(){}

    public static ToastUtil getIns(){
        if(ins == null){
            synchronized (ToastUtil.class) {
                if(ins == null){
                    ins = new ToastUtil();
                }
            }
        }
        return ins;
    }
	
	private Toast mToast;

	private Handler mHandler = new Handler(Looper.getMainLooper());
	private Runnable r = new Runnable() {
		public void run() {
			if(mToast != null) {
				mToast.cancel();
				mToast = null;// toast隐藏后，将其置为null
			}
		}
	};

    public void show(Context context, int message) {
        show(context, context.getString(message));
    }

    public void show(final Context context, final String message) {
        if(message != null && message.length() > 0){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(mToast != null) {
                        mToast.cancel();
                        mToast = null;// toast隐藏后，将其置为null
                    }
                    TextView text = new TextView(context);// 显示的提示文字
                    text.setText(message);
                    text.setPadding(30, 30, 30, 30);
                    text.setTextColor(Color.WHITE);
                    text.setBackgroundResource(R.drawable.r45_000000);

                    mToast = new Toast(context);
                    mToast.setDuration(Toast.LENGTH_SHORT);
                    mToast.setGravity(Gravity.CENTER, 0, 0);
                    mToast.setView(text);
                    mHandler.postDelayed(r, 2000);// 延迟1秒隐藏toast
                    mToast.show();
                }
            });
        }
    }

    public static void release(){
        ins = null;
    }

}