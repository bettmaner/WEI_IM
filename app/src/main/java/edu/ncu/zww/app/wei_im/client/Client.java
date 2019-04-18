package edu.ncu.zww.app.wei_im.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.utils.LogUtil;

public class Client {
    private static Client mInstance; //单例实例对象
    private Socket clientSocket;  // 连接的socket
    private String ip;  // client的ip地址
    private int port;    // client的端口
    private boolean mIsConnected = false; // 客户端与服务端的连接状态
    private  ClientInputThread mClientIn;
    private ClientOutputThread mClientOut;

    private Client(){}

    // 该方法获取单例对象
    public static Client getInstance() {
        if (mInstance == null) {
            mInstance = new Client();
        }
        return mInstance;
    }

    // 客户端初始化
    public void init(String ip, int port) {
        this.ip = ip;
        this.port =port;
    }

    // 建立客户端与服务端的连接
    public boolean startConnect() {
        try {
            // client = new Socket(ip, port);
            // 通过socket建立连接
            clientSocket = new Socket();
            clientSocket.connect(new InetSocketAddress(ip,port), 1000);
            LogUtil.d("创建client： ip为" + ip + " ,port:" + port);

            if (clientSocket.isConnected()) {
                mIsConnected =true;
                LogUtil.d("客户端已经连接上啦");
//                clientThread = new ClientThread(client);
//                clientThread.start();
                startClientIO(); // 开启client读写线程
            } else {
                mIsConnected = false;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            LogUtil.d("Network", "服务器地址无法解析");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.d("Network", "Socket io异常");
            return false;
        }
        return true;
    }

    public boolean isConnected() {
        return mIsConnected;
    }

    // 创建、开启clien的读线程和写线程
    private void startClientIO() {
        if (mIsConnected) {
            mClientIn = new ClientInputThread(clientSocket); // 实例化读线程
            mClientOut = new ClientOutputThread(clientSocket); // 实例化写线程
            /* 运行线程 */
            mClientIn.start();
//            mClientOut.start();
        }
    }

    // 获取读线程
    public ClientInputThread getClientInputThread() {
        return mClientIn;
    }

    // 获取写线程
    public ClientOutputThread getClientOutputThread() {
        return mClientOut;
    }

    // 向服务器发送序列化对象TranObject
    public void send(TranObject t) throws IOException {
        mClientOut.sendMsg(t);
    }

    // 关闭客户端与服务端的连接
    public void closeConnection() {
        if (mClientOut != null) {
//            mClientOut.close();
            mClientOut = null;
        }
        if (mClientIn != null) {
            mClientIn.close();
            mClientIn = null;
        }
        try {
            if (clientSocket != null)
                clientSocket.close();
                clientSocket = null;
                mIsConnected = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
