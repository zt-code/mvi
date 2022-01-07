package com.zt.socket;

public class ProtocolConstants {

    public static final int MAIN_ID_MASK = 0xFFFF0000; //主ID掩码
    public static final int SUB_ID_MASK =  0x0000FFFF;//子ID掩码

    /**
     * 心跳包
     */
    public static final int HEART_BEAT = 0x0000FFFF;

    //=============================MainID = 0x00010000=======================================
    //MainID = 0x00010000  //认证授权
    public static final int AUTH_LOGIN_TOKEN = 0x00010001;  //subID = 0x00000001

    //返回码
    public static final int RETURN_SUCCESS_CODE = 1;

    public static final int RETURN_FAIL_LOGIN_CODE = 3000;  //登录认证失败

}