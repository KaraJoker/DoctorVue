package com.chero.client.utils;

import java.util.UUID;

/**
 * Created by hxh on 2018/5/17.
 */
public class UUIDUtil {

    private UUIDUtil() {}

    public static String getUUID(){
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }
}