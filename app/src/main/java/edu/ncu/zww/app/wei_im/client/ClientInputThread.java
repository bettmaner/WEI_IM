package edu.ncu.zww.app.wei_im.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.utils.MyObjectInputStream;

/*
*  client的读线程
* */
public class ClientInputThread extends Thread {
    private Socket socket;
    private TranObject msg;
    private boolean isStart = true;
    private ObjectInputStream ois;
    private MessageListener messageListener;// 消息监听接口对象

    public ClientInputThread(Socket socket) {
        this.socket = socket;
        try {
            ois = new MyObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提供给外部的消息监听方法（在GetMsgService和FriendActivity使用）
     *
     * @param messageListener
     *            消息监听接口对象
     */
    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    @Override
    public void run() {
        try {
            while (isStart) {
                msg = (TranObject) ois.readObject();
                System.out.println(msg.getClass()+"\n"+msg.toString());

                // 处理得到的消息，详见GetMsgService的Message方法，处理消息是显示在通知栏还是广播出去
                messageListener.Message(msg);
            }
            ois.close();
            if (socket != null)
                socket.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
