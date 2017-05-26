package com.study.zouchao.finalexamproject_two.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 */

public class MD5Util {
    /**
     * 将字符串url 转换为 MD5的key
     * @param url
     */
    public static String haskKeyFromUrl(String url) {
        String cacheKey;
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytes2HexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {

            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private static String bytes2HexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i ++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
