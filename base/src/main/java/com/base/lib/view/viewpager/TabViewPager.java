package com.base.lib.view.viewpager;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * 用来规避tabLayout点击切换动画的ViewPager
 * setScroll(false)
 */
public class TabViewPager extends ViewPager {

    //定义一个变量，用来设置是否有切换动画，支持配置
    private boolean scroll = false;

    public TabViewPager(@NonNull Context context) {
        super(context);
    }

    public TabViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        //这里读取设置的值
        super.setCurrentItem(item, scroll);
    }

    @Override
    public void setCurrentItem(int item) {
        //这里读取设置的值
        super.setCurrentItem(item, scroll);
    }

    //设置方法
    public void setScroll(boolean scroll) {
        this.scroll = scroll;
    }

}
