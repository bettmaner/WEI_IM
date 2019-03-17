package edu.ncu.zww.app.wei_im.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType;

/**
 *  客户端写消息线程
 * */
public class ClientOutputThread extends Thread{
    private Socket socket;
    private ObjectOutputStream out;

    boolean isStart = true;
    private TranObject msg;

    public ClientOutputThread(Socket socket) {
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    public void setMsg(TranObject msg) {
        LogUtil.d("ClientOutputThread向服务器发送消息了："+ msg);
        this.msg = msg;
        synchronized (this) {
            notify();
        }
    }

    public void run() {
        try {
            while (isStart) {
                if (msg != null) {
                    out.writeObject(msg);
                    out.writeObject(null);
                    out.flush();
                    LogUtil.d("ClientOutputThread发送msg为"+msg+msg.getType().getClass());
                    if (msg.getType() == TranObjectType.LOGOUT) {// 如果是发送下线的消息，就直接跳出循环
                        break;
                    }
                    synchronized (this) {
                        wait();// 发送完消息后，线程进入等待状态
                    }
                }
            }
            out.close();// 循环结束后，关闭输出流和socket
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
