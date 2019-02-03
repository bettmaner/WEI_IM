package edu.ncu.zww.app.wei_im.utils;

import android.util.Log;

public class LogUtil {
    /**正式上线時候设为false*/
    private final static boolean debug = true;
    private final static String TAG = "weiim";

    public static void v(String tag, String msg) {
        if (debug) {
            Log.v(TAG+"_"+tag, msg);
        }
    }

    public static void v(String msg) {
        if (debug) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (debug) {
            Log.d(TAG+"_"+tag, msg);
        }
    }

    public static void d(String msg) {
        if (debug) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (debug) {
            Log.i(TAG+"_"+tag, msg);
        }

    }

    public static void i(String msg) {
        if (debug) {
            Log.i(TAG, msg);
        }

    }

    public static void w(String tag, String msg) {
        if (debug) {
            Log.w(TAG+"_"+tag, msg);
        }
    }

    public static void w(String msg) {
        if (debug) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (debug) {
            Log.e(TAG+"_"+tag, msg);
        }
    }

    public static void e(String msg) {
        if (debug) {
            Log.e(TAG, msg);
        }
    }
}
