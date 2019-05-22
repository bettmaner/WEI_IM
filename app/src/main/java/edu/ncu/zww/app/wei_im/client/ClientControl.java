package edu.ncu.zww.app.wei_im.client;

import java.io.IOException;
import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupInfo;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Invitation;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Message;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType;
import edu.ncu.zww.app.wei_im.mvp.model.bean.User;
import edu.ncu.zww.app.wei_im.utils.BeanTransfer;

import static edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType.FRIEND_REQUEST;
import static edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType.GROUP_REQUEST;
import static edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType.MESSAGE;

public class ClientControl {
    private static Client mClient = Client.getInstance();
    private static User mUser;
    // 发送注册的用户信息
    public static void register(User user) throws IOException {
        TranObject<User> t = new TranObject<>(TranObjectType.REGISTER);
        t.setObject(user);
        mClient.send(t);

    }

    // 发送登录的用户信息
    public static void login(User user) throws IOException {
        mUser = user;
        TranObject<User> t = new TranObject<>(TranObjectType.LOGIN);
        t.setObject(user);
        mClient.send(t);
    }

    public static void LogOff() throws IOException {
        TranObject<User> t = new TranObject<>(TranObjectType.LOGOFF);
        mClient.send(t);
    }

//    // 已经使用http实现
//    public static void searchContacts(String account,Integer type) throws IOException {
//        TranObject<String> t;
//        if (type == 0){
//           t = new TranObject<>(TranObjectType.LOGIN);
//        } else {
//            t = new TranObject<>(TranObjectType.LOGIN);
//        }
//
//        t.setObject(account);
//        mClient.send(t);
//    }

    // 发送人或者群的邀请信息
    public static void sendConOrGroupRequest(Invitation invitation,String tranType) throws IOException {
        TranObject t = new TranObject<Invitation>(tranType);
        t.setFromUser(ApplicationData.getInstance().getUserInfo().getAccount());
        t.setToUser(invitation.getAccount());
        t.setObject(invitation);
        mClient.send(t);
    }


    // 创建群聊
    public static void createGroup(GroupInfo group) throws IOException {
        TranObject t = new TranObject<GroupInfo>(TranObjectType.CREATE_GROUP);
        t.setObject(group);
        t.setInfo("创建群聊");
        mClient.send(t);
    }

    // 获取所有群聊
    public static void getGroupList() throws IOException {
        TranObject t = new TranObject<GroupInfo>(TranObjectType.GET_ALL_GROUPS);
        t.setInfo("获取用户群聊");
        mClient.send(t);
    }

    public static void getGroupMember(Integer groupId) throws IOException {
        TranObject t = new TranObject<GroupInfo>(TranObjectType.GET_GROUP_MEMBER);
        t.setInfo("获取"+groupId+"群成员");
        t.setObject(groupId);
        mClient.send(t);
    }

//    public static void sendMessage(ChatEntity message) {
//
//        TranObject t = new TranObject();
//        t.setTranType(TranObjectType.MESSAGE);
//        t.setReceiveId(message.getReceiverId());
//        t.setSendName(ApplicationData.getInstance().getUserInfo().getUserName());
//        t.setObject(message);
//        try {
//            mNetService.send(t);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    // 发送消息
    public static void sendMsg(Message message) throws IOException {
        TranObject tranObject = new TranObject<Message>(MESSAGE);
        tranObject.setFromUser(mUser.getAccount());
        tranObject.setToUser(message.getReceiveId());
        tranObject.setObject(message);
        mClient.send(tranObject);
    }
}
