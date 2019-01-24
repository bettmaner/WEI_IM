package edu.ncu.zww.app.wei_im.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import edu.ncu.zww.app.wei_im.utils.LogUtil;

public class GetMsgService {

    // 收到用户按返回键发出的广播，就显示通知栏
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "QQ进入后台运行", 0).show();
            LogUtil.d("QQ进入后台运行");
            MsgNotification();
        }
    };


    private void MsgNotification() {
    }
}
