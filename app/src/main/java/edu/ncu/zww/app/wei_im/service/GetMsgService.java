package edu.ncu.zww.app.wei_im.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import edu.ncu.zww.app.wei_im.client.Client;
import edu.ncu.zww.app.wei_im.client.ClientInputThread;
import edu.ncu.zww.app.wei_im.client.MessageListener;
import edu.ncu.zww.app.wei_im.commons.Constants;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType;
import edu.ncu.zww.app.wei_im.test.TestInput;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import edu.ncu.zww.app.wei_im.utils.NetWorkUtil;
import edu.ncu.zww.app.wei_im.utils.SharePreferenceUtil;
/*
*  后台运行
*  MessageListener获取服务器传来的消息，若后台运行则handle处理并进行通知，否则广播发送消息
*  负责下线通知服务器
* */
public class GetMsgService extends Service {

    private static final int MSG = 0x001;
//    private MApplication application;
    private Client client;
    private SharePreferenceUtil util;

    private TestInput testInput;


    // 收到用户按返回键发出的广播，就显示通知栏
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 如果是网络监听
            if (Constants.NET_ACTION.equals(intent.getAction())) {
//                if (NetWorkUtil.isNetWorkAvailable() && !client.isConnected()) {
//                    System.out.println("有网啦！！自动重连服务器中！！");
//                    clientConnect();
//                } else {
//                    System.out.println("网络迷路了！！");
//                }
            } else {
                Toast.makeText(context, "QQ进入后台运行", Toast.LENGTH_SHORT).show();
                LogUtil.d("QQ进入后台运行");
                MsgNotification();
            }

        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            LogUtil.d("GetMsgService: 回调handle得到msg，准备通知状态栏");
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

        // 连接服务器
        client = Client.getInstance();
        if(!client.isConnected()) {
            clientConnect();
        }

        // 注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BACKKEY_ACTION);
        intentFilter.addAction(Constants.NET_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
        // 获取 NotificationManager



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("GetMsgService的onStartCommand被调用"+this);
        return super.onStartCommand(intent, flags, startId);
    }

    // 客户端连接服务器
    public void clientConnect() {
        if(!NetWorkUtil.isNetWorkAvailable()) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                // 开始连接客户端与服务端，并返回boolean结果
                if (client.startConnect()) {

                    // 设置写线程的消息监听接口
                    ClientInputThread in = client.getClientInputThread();
                    in.setMessageListener(new MessageListener() {
                        //                    testInput.setMessageListener(new MessageListener() {
                        @Override
                        public void Message(TranObject msg) {
                            // 只有在FriendListActivity中设置了util的isStart为true
                            if (SharePreferenceUtil.getInstance().getIsStart()) {// 如果
                                // 是在后台运行，就更新通知栏，否则就发送广播给Activity
                                LogUtil.d("GetService: 后台运行中，准备更新通知栏");
//                                if (msg.getType() == TranObjectType.MESSAGE) {// 只处理文本消息类型
                                // System.out.println("收到新消息");
                                // 把消息对象发送到handler去处理
                                Message message = handler.obtainMessage();
                                message.what = MSG;
                                message.getData().putSerializable("msg",
                                        msg);
                                handler.sendMessage(message);
                            }
//                            } else {
//                                Intent intent = new Intent();
//                                intent.setAction(Constants.ACTION);
//                                intent.putExtra(Constants.MSGKEY, msg);
//                                sendBroadcast(intent);// 把收到的消息以广播的形式发送出去
//                                LogUtil.d("GetService: 后台运行中，将读线程传来的消息广播出去");
//                            }
                        }
                    });
                }
            }

        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("GetMsgService的onDestroy被调用");
        // 注销广播
        unregisterReceiver(broadcastReceiver);
        // 取消通知栏

        // 给服务器发送下线通知

    }
}
