package com.chero.client.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by hxh on 2017/12/27.
 *
 * @author hxh
 */



public class Base64Util {
    /**
     * @param string
     * @return
     */
    public static String decode(final String string) {
        try {
            return new String(Base64.decodeBase64(string.getBytes("UTF-8")), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param string
     * @return
     * @throws Exception
     */
    public static String encode(final String string) {
        try {
            return new String(Base64.encodeBase64(string.getBytes("UTF-8")),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
