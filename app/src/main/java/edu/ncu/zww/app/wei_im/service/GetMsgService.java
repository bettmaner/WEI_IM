package edu.ncu.zww.app.wei_im.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;


import edu.ncu.zww.app.wei_im.MApplication;
import edu.ncu.zww.app.wei_im.client.Client;
import edu.ncu.zww.app.wei_im.commons.Constants;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import edu.ncu.zww.app.wei_im.utils.SharePreferenceUtil;

public class GetMsgService extends Service {

    private static final int MSG = 0x001;
    private MApplication application;
    private Client client;
    private boolean isStart = false;// 是否与服务器连接上
    private SharePreferenceUtil util;



    // 收到用户按返回键发出的广播，就显示通知栏
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "QQ进入后台运行", Toast.LENGTH_SHORT).show();
            LogUtil.d("QQ进入后台运行");
            MsgNotification();
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //int newMsgNum = application.
            }
        }
    };



    private void MsgNotification() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("GetMsgService的onCreate被调用");
        application = MApplication.getInstance();
        client = application.getClient();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("GetMsgService的onStartCommand被调用");

        util = new SharePreferenceUtil(getApplicationContext(),
                Constants.SAVE_USER);
        new Thread() {
            @Override
            public void run() {
                isStart = client.start();
                application.setConnected(isStart);
                System.out.println("client start:" + isStart);
                /*if (isStart) {
                    ClientInputThread in = client.getClientInputThread();
                    in.setMessageListener(new MessageListener() {

                        @Override
                        public void Message(TranObject msg) {
                            // System.out.println("GetMsgService:" + msg);
                            if (util.getIsStart()) {// 如果
                                // 是在后台运行，就更新通知栏，否则就发送广播给Activity
                                if (msg.getType() == TranObjectType.MESSAGE) {// 只处理文本消息类型
                                    // System.out.println("收到新消息");
                                    // 把消息对象发送到handler去处理
                                    Message message = handler.obtainMessage();
                                    message.what = MSG;
                                    message.getData().putSerializable("msg",
                                            msg);
                                    handler.sendMessage(message);
                                }
                            } else {
                                Intent broadCast = new Intent();
                                broadCast.setAction(Constants.ACTION);
                                broadCast.putExtra(Constants.MSGKEY, msg);
                                sendBroadcast(broadCast);// 把收到的消息已广播的形式发送出去
                            }
                        }
                    });
                }*/
            }

        }.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("GetMsgService的onDestroy被调用");
    }
}
