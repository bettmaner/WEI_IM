package edu.ncu.zww.app.wei_im.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import edu.ncu.zww.app.wei_im.utils.MyObjectInputStream;

import static edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType.CREATE_GROUP;
import static edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType.FRIEND_REQUEST;
import static edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType.GET_ALL_GROUPS;
import static edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType.GET_GROUP_MEMBER;
import static edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType.LOGIN;
import static edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType.MESSAGE;
import static edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType.REGISTER;

/*
*  client的读线程
* */
public class ClientInputThread extends Thread {
    private Socket socket;
//    private TranObject msg;
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

//    public void setStart(boolean isStart) {
//        this.isStart = isStart;
//    }

    @Override
    public void run() {
        try {
            while (isStart) {
                TranObject resultData = null;
                // 对象输出流
                Object oMsg = ois.readObject();
                if ((oMsg != null && oMsg instanceof TranObject)) {
                    resultData = (TranObject) oMsg;// 转换成传输对象
                }
                System.out.println("-------------------- 服务端发来数据-----------------------");
                System.out.println(resultData);
                System.out.println("----------------------------------------------------------");
                LogUtil.d("ClientInput","线程:" + Thread.currentThread().getName());
                ApplicationData mData = ApplicationData.getInstance();
                // 以mData为锁
                synchronized(mData){
                    // ismIsReceived为真表示有线程在取值，所以应该先等待
                    if(mData.isIsReceived()){
                        try{
                            mData.wait();
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    switch (resultData.getType()) {
                        case REGISTER:
                            mData.setRegisterResult(resultData);
                            break;
                        case LOGIN:
                            mData.setLoginResult(resultData);
                            break;
//                    case SEARCH_FRIEND:
//                        System.out.println("收到朋友查找结果");
//                        SearchFriendActivity.messageArrived(resultData);
//                        break;
                        case FRIEND_REQUEST:
                            mData.dealFriendRequest(resultData);
                            /**
                             *   判断该请求是别人发过来的还是服务器响应自己的操作
                             *  属于前者则需要messageListener转到service显示通知栏提醒
                             */
                            int userAccount = ApplicationData.getInstance().getUserInfo().getAccount();
                            // 如果toUser是自己，则属于后者情况
                            if (resultData.getToUser()==userAccount) {
                                System.out.println("邀请转交给service显示通知栏");
                                messageListener.Message(resultData); // 转到GetMsgService，负责显示通知
                            }
                            break;
                        case CREATE_GROUP:
                            mData.createGroup(resultData);
                            break;
                        case GET_ALL_GROUPS:
                            mData.getAllGroups(resultData);
                            break;
                        case GET_GROUP_MEMBER:
                            mData.rcGroupMember(resultData);
                            break;
                        case MESSAGE:
//                        ApplicationData.getInstance().messageArrived(resultData);

                            // 处理得到的消息，详见GetMsgService的Message方法，处理消息是显示在通知栏还是广播出去
                            messageListener.Message(resultData);
//                            mData.saveMessage(resultData);
                            break;
                        default:
                            break;
                    }
                    // 数据已更新完，设置为true，因为其它线程根据其可取
                    mData.setIsReceived(true);
                    // 唤醒之前被锁的线程
                    mData.notify();

                }

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

    public void close() {
        isStart = false;
    }
}
