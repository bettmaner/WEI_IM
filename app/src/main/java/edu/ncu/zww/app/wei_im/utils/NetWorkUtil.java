package edu.ncu.zww.app.wei_im.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtil {

    private static Context mContext;

    public static void initialize(Context context) {
        mContext = context;
    }

    public static boolean isNetWorkAvailable() {
        ConnectivityManager manager
                = (ConnectivityManager) mContext.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }


}
