package edu.ncu.zww.app.wei_im.utils;

import java.util.UUID;

public class NumberUtil {

    public static String getUUID() {
       return Long.toString(UUID.randomUUID().getLeastSignificantBits());
    }
}
