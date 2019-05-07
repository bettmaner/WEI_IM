package edu.ncu.zww.app.wei_im.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;

public class DialogFactory {
    public static void ToastDialog(Context context, String title, String msg) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(msg)
                .setPositiveButton("确定", null).create().show();
    }
}
