package com.base.lib.base;

public class Tag {

    public static String UI = "UI";
    public static String Result = "Result";

    public static String ActName = "ActName";
    public static String ActTime = "ActTime";
    public static String ActKey = "ActKey";
    public static String ActData = "ActKey";

    public static String FrgName = "FrgName";
    public static String FrgTime = "FrgTime";
    public static String FrgKey = "FrgKey";
    public static String FrgData = "FrgData";


    public static int ON_FIRST = 0;    //页面首次加载
    public static int ON_RESTART = 1;  //页面重新打开
    public static int ON_PRELOAD = 2;  //页面处于预加载状态(处于ViewPager中，页面已onCreate但未显示)
    public static int ON_SEL_TAB = 3;  //ViewPager滑动
    public static int ON_CLICK = 4;  //ViewPager滑动
    public static int ON_HIDDEN = 5;   //页面处于隐藏状态(ViewPager划走，或者APP处于后台)

}
