package com.base.lib.statusbar;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class StatusBarRLayout extends RelativeLayout {

    private int statusBarHeight;

    public StatusBarRLayout(Context context) {
        super(context);
        init(context);
    }

    public StatusBarRLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StatusBarRLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            statusBarHeight = getStatusBarHeight(context);
            setPadding(0, statusBarHeight,0,0);
        }else{
            //低版本 直接设置0
            statusBarHeight = 0;
            setPadding(0, statusBarHeight,0,0);
        }
    }

    public int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
 
}