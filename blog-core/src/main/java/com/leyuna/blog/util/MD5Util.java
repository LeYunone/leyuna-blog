package com.leyuna.blog.util;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-04-21
 * MD5码处理工具
 */
public class MD5Util {

    /**
     * 文件转MD5码
     * @return
     */
    public static String fileToMD5(byte [] bytesFile){
        String value = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(bytesFile);
            BigInteger bi = new BigInteger(1, digest);
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
