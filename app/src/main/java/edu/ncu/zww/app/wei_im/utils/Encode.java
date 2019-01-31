package edu.ncu.zww.app.wei_im.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encode {
    public static String getEncode(String codeType, String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance(codeType);// 获取一个实例，并传入加密方式
            digest.reset(); // 重置摘要以便下次使用
            digest.update(content.getBytes());  // 写入内容,可以指定编码方式content.getBytes("utf-8");
            StringBuilder builder = new StringBuilder();
            for (byte b : digest.digest()) {
                builder.append(Integer.toHexString((b >> 4) & 0xf));
                builder.append(Integer.toHexString(b & 0xf));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
