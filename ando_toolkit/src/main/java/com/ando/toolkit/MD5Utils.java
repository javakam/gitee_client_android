package com.ando.toolkit;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * Title: MD5Utils
 * <p>
 * Description:
 * </p>
 *
 * @author javakam
 * @date 2019/11/13  9:23
 */
public class MD5Utils {


    public static String getKey(String deviceId, String key, String time) {
        return md5Decode32(String.format(Locale.getDefault(), "%s%s%s", deviceId, key, time));
    }

    public static String getKey(String key, String time) {
        return md5Decode32(String.format(Locale.getDefault(), "%s%s", key, time));
    }

    /**
     * 32位MD5加密
     *
     * @param content 待加密内容
     * @return
     */
    public static String md5Decode32(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
        //对生成的16字节数组进行补零操作
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 16位MD5加密
     * 实际是截取的32位加密结果的中间部分(8-24位)
     *
     * @param content
     * @return
     */
    public static String md5Decode16(String content) {
        return md5Decode32(content).substring(8, 24);
    }
}
