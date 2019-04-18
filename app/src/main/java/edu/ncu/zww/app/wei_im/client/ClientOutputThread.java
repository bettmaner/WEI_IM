package edu.ncu.zww.app.wei_im.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType;
import edu.ncu.zww.app.wei_im.utils.LogUtil;

/**
 *  客户端写消息线程
 *  这里没继承线程类因为在请求数据再开个线程.
 * */
public class ClientOutputThread {
    private Socket socket;
    private ObjectOutputStream out;

//    boolean isStart = true; // 如果为true，该线程一直在循环中执行
//    private TranObject msg;

    public ClientOutputThread(Socket socket) {
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void setStart(boolean isStart) {
//        this.isStart = isStart;
//    }

    // 更新msg,并唤醒线程进行发送
//    public void setMsg(TranObject msg) {
//        this.msg = msg;
//        synchronized (this) {
//            notify();
//        }
//    }

    public void sendMsg(TranObject msg) throws IOException {
        if (msg != null) {
            out.writeObject(msg);
            out.writeObject(null);
            out.flush();
        }
    }

    // ClientOutputThread线程一执行就读取msg并发送，发送完毕则进入阻塞
//    public void run() {
//        try {
//            while (isStart) {
//                if (msg != null) {
//                    out.writeObject(msg);
//                    out.writeObject(null);
//                    out.flush();
//                    LogUtil.d("ClientOutputThread发送msg为"+msg+msg.getType().getClass());
//                    if (msg.getType() == TranObjectType.LOGOUT) {// 如果是发送下线的消息，就直接跳出循环
//                        break;
//                    }
//                    synchronized (this) {
//                        wait();// 发送完消息后，线程进入等待状态
//                    }
//                }
//            }
//            out.close();// 循环结束后，关闭输出流和socket
//            if (socket != null)
//                socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

//    public void close() {
//        isStart = false;
//    }
}
