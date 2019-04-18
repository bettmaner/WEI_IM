package edu.ncu.zww.app.wei_im.client;

import java.io.IOException;

import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType;
import edu.ncu.zww.app.wei_im.mvp.model.bean.User;
import edu.ncu.zww.app.wei_im.utils.UserFilter;

import static edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType.FRIEND_REQUEST;
import static edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType.GROUP_REQUEST;

public class ClientControl {
    private static Client mClient = Client.getInstance();

    // 发送注册的用户信息
    public static void register(User user) throws IOException {
        TranObject<User> t = new TranObject<>(TranObjectType.REGISTER);
        t.setObject(user);
        mClient.send(t);

    }

    // 发送登录的用户信息
    public static void login(User user) throws IOException {
        TranObject<User> t = new TranObject<>(TranObjectType.LOGIN);
        t.setObject(user);
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

    public static void sendContactRequest(Integer account, Integer type) throws IOException {
        String typeStr = type==0 ? FRIEND_REQUEST : GROUP_REQUEST;
        TranObject t = new TranObject<User>(typeStr);
        t.setToUser(account);
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss");
//        String sendTime = sdf.format(date);
//        t.setSendTime(sendTime);
        User user = UserFilter.filterUser(ApplicationData.getInstance().getUserInfo());
        t.setFromUser(user.getAccount());
        t.setObject(user); // 传入的object是自己的名字
        t.setStatus(2); // 等待确认
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
}
