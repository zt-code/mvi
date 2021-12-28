package com.zt.mvi.demo.refresh;

import com.alibaba.fastjson.JSON;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class SecureUtil {

    private static final String key = "07d966f2f636a02464608011f2bceb02";

    public static String getKey() {
        return key;
    }

    /**
     * 生成签名     * @param parameters     * @param key 商户ID     * @return
     */
    public static String createSign(String str)  {
        SortedMap<String, String> parameters = JSON.parseObject(str, SortedMap.class);
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        sb.append("key=").append(key);
        //mobile=13177203220 & password=1bbd886460827015e5d605ed44252251 & key=07d966f2f636a02464608011f2bceb02
        String sign = null;
        try {
            sign = md5(sb.toString()).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }

    public static String md5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
}