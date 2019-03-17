package edu.ncu.zww.app.wei_im.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import edu.ncu.zww.app.wei_im.utils.LogUtil;

public class Client {
    private Socket client;
    private String ip;
    private int port;
    private ClientThread clientThread;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public boolean start() {
        try {
            // client = new Socket(ip, port);
            client = new Socket();
            LogUtil.d("创建client： ip为" + ip + " ,port:" + port);
            client.connect(new InetSocketAddress(ip,port), 1000);

            if (client.isConnected()) {
                LogUtil.d("客户端已经连接上啦");
                clientThread = new ClientThread(client);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return  true;
    }

    // 直接通过client得到读线程
    public ClientInputThread getClientInputThread() {
        return clientThread.getIn();
    }

    // 直接通过client得到写线程
    public ClientOutputThread getClientOutputThread() {
        return clientThread.getOut();
    }

    // 直接通过client停止读写消息
    public void setIsStart(boolean isStart) {
        clientThread.getIn().setStart(isStart);
        clientThread.getOut().setStart(isStart);
    }

    public class ClientThread extends Thread {

        private ClientInputThread in;
        private ClientOutputThread out;

        public ClientThread(Socket socket) {
            in = new ClientInputThread(socket);
            out = new ClientOutputThread(socket);
        }

        public void run() {
            in.setStart(true);
            out.setStart(true);
            in.start();
            out.start();
        }

        // 得到读消息线程
        public ClientInputThread getIn() {
            return in;
        }

        // 得到写消息线程
        public ClientOutputThread getOut() {
            return out;
        }
    }
}
