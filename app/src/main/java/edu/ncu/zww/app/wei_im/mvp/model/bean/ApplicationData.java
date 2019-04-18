package edu.ncu.zww.app.wei_im.mvp.model.bean;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import edu.ncu.zww.app.wei_im.utils.LogUtil;
import edu.ncu.zww.app.wei_im.utils.SharePreferenceUtil;

public class ApplicationData {
    private static ApplicationData mInitData; // 该类的单例实例
//    private T currentModel;
    private static SharePreferenceUtil spUtil;
    private User mUser; // 个人数据
    private boolean mIsReceived ;   // 是否已接收完数据
    private TranObject mReceivedMessage;    // 服务器最新传来的信息
//    private TranObject<User> registerResult; // 注册返回的数据
    private List<User> mFriendList; // 好友数据
//    private Map<Integer, Bitmap> mFriendPhotoMap;
//    private Handler messageHandler;
//    private Handler chatMessageHandler;
//    private Handler friendListHandler;
    private Context mContext;
//    private List<User> mFriendSearched;
//    private Bitmap mUserPhoto;
//    private List<MessageTabEntity> mMessageEntities;// messageFragment显示的列表
//    private Map<Integer, List<ChatEntity>> mChatMessagesMap;

//    public Map<Integer, List<ChatEntity>> getChatMessagesMap() {
//        return mChatMessagesMap;
//    }

//    public void setChatMessagesMap(
//            Map<Integer, List<ChatEntity>> mChatMessagesMap) {
//        this.mChatMessagesMap = mChatMessagesMap;
//    }

    public static ApplicationData getInstance() {
        if (mInitData == null) {
            mInitData = new ApplicationData();
            spUtil = SharePreferenceUtil.getInstance();
        }
        return mInitData;
    }

    private ApplicationData() {}

    // 初始化本对象的所有数据
    public void initData() {
        System.out.println("initdata");
        mIsReceived = false;
        mFriendList = null;
        mUser = null;
        mReceivedMessage = null;
    }

    public void setIsReceived(boolean mIsReceived) {
        this.mIsReceived = mIsReceived;
    }

    public void setReceivedMessage(TranObject mReceivedMessage) {
        this.mReceivedMessage = mReceivedMessage;
    }


    /**
     *  注册返回的数据.
     *  泛型为User,为个人信息数据.
     *  */
    public void setRegisterResult(TranObject tranObject) {
        this.mReceivedMessage = tranObject;
    }

    /**
     *  登录返回的数据.
     *  泛型为List<User>。登录失败则为空，成功则第一个是个人信息，后面是好友信息
     *  */
    public void setLoginResult(final TranObject tranObject) {
        LogUtil.d("处理登录返回数据的运行线程:" + Thread.currentThread().getName());
        // 1.重置mReceivedMessage
        this.mReceivedMessage = tranObject;
        // 2.自动处理结果
        if (tranObject.getStatus() == 0) { // 登陆成功
            List<User> list = (List<User>) tranObject.getObject();
            // a.设置个人信息
            this.mUser = list.get(0);
            // b.设置好友信息
            this.mFriendList = new ArrayList<>();
            this.mFriendList.addAll(list.subList(1,list.size()));
        } else {
            mUser = null;
            mFriendList = null;
        }
        // mChatMessagesMap = new HashMap<Integer, List<ChatEntity>>();
//        mIsReceived = true; // 最新消息更新状态
    }


//    public void loginMessageArrived(Object tranObject) {
//
//        mReceivedMessage = (TranObject) tranObject;
//        Result loginResult = mReceivedMessage.getResult();
//        if (loginResult == Result.LOGIN_SUCCESS) {
//            mUser = (User) mReceivedMessage.getObject();
//            mFriendList = mUser.getFriendList();// 根据从服务器得到的信息，设置好友是否在线
//            mUserPhoto = PhotoUtils.getBitmap(mUser.getPhoto());
//            List<User> friendListLocal = ImDB.getInstance(mContext)
//                    .getAllFriend();
//            mFriendPhotoMap = new HashMap<Integer, Bitmap>();
//            for (int i = 0; i < friendListLocal.size(); i++) {
//                User friend = friendListLocal.get(i);
//                Bitmap photo = PhotoUtils.getBitmap(friend.getPhoto());
//                mFriendPhotoMap.put(friend.getId(), photo);
//            }
//            mMessageEntities = ImDB.getInstance(mContext).getAllMessage();
//        } else {
//
//            mUser = null;
//            mFriendList = null;
//        }
//        mChatMessagesMap = new HashMap<Integer, List<ChatEntity>>();
//        mIsReceived = true;
//    }
//
//    public Map<Integer, Bitmap> getFriendPhotoMap() {
//        return mFriendPhotoMap;
//    }
//
//    public void setFriendPhotoList(Map<Integer, Bitmap> mFriendPhotoMap) {
//        this.mFriendPhotoMap = mFriendPhotoMap;
//    }
//


    /** Getter方法 */
    public boolean isIsReceived() {
        return mIsReceived;
    }

    public TranObject getReceivedMessage() {
        return mReceivedMessage;
    }


    public User getUserInfo() {
        if (mUser!=null) {
            return mUser;
        } else {
            mUser = new User();
            mUser.setName(spUtil.getName());
            mUser.setImg(spUtil.getImg());
            mUser.setAccount(spUtil.getId());
            mUser.setSex(spUtil.getSex());
            mUser.setEmail(spUtil.getEmail());
            return mUser;
        }

    }

    public List<User> getFriendList() {
        return mFriendList;
    }





//    public List<User> getFriendSearched() {
//        return mFriendSearched;
//    }
//
//    public void setFriendSearched(List<User> mFriendSearched) {
//        this.mFriendSearched = mFriendSearched;
//    }
//
//    public void friendRequestArrived(TranObject mReceivedRequest) {
//        MessageTabEntity messageEntity = new MessageTabEntity();
//        if (mReceivedRequest.getResult() == Result.MAKE_FRIEND_REQUEST) {
//            messageEntity.setMessageType(MessageTabEntity.MAKE_FRIEND_REQUEST);
//            messageEntity.setContent("希望加你为好友");
//        } else if (mReceivedRequest.getResult() == Result.FRIEND_REQUEST_RESPONSE_ACCEPT) {
//            messageEntity
//                    .setMessageType(MessageTabEntity.MAKE_FRIEND_RESPONSE_ACCEPT);
//            messageEntity.setContent("接受了你的好友请求");
//            User newFriend = (User) mReceivedRequest.getObject();
//            if (!mFriendList.contains(newFriend)) {
//
//                mFriendList.add(newFriend);
//            }
//
//            mFriendPhotoMap.put(newFriend.getId(),
//                    PhotoUtils.getBitmap(newFriend.getPhoto()));
//            if (friendListHandler != null) {
//                Message message = new Message();
//                message.what = 1;
//                friendListHandler.sendMessage(message);
//            }
//            ImDB.getInstance(mContext).saveFriend(newFriend);
//        } else {
//            messageEntity
//                    .setMessageType(MessageTabEntity.MAKE_FRIEND_RESPONSE_REJECT);
//            messageEntity.setContent("拒绝了你的好友请求");
//        }
//        messageEntity.setName(mReceivedRequest.getSendName());
//        messageEntity.setSendTime(mReceivedRequest.getSendTime());
//        messageEntity.setSenderId(mReceivedRequest.getSendId());
//        messageEntity.setUnReadCount(1);
//        ImDB.getInstance(mContext).saveMessage(messageEntity);
//        mMessageEntities.add(messageEntity);
//        if (messageHandler != null) {
//            Message message = new Message();
//            message.what = 1;
//            messageHandler.sendMessage(message);
//        }
//    }
//
//    public void messageArrived(TranObject tran) {
//        ChatEntity chat = (ChatEntity) tran.getObject();
//        int senderId = chat.getSenderId();
//        System.out.println("senderId" + senderId);
//        boolean hasMessageTab = false;
//        for (int i = 0; i < mMessageEntities.size(); i++) {
//            MessageTabEntity messageTab = mMessageEntities.get(i);
//            if (messageTab.getSenderId() == senderId
//                    && messageTab.getMessageType() == MessageTabEntity.FRIEND_MESSAGE) {
//                messageTab.setUnReadCount(messageTab.getUnReadCount() + 1);
//                messageTab.setContent(chat.getContent());
//                messageTab.setSendTime(chat.getSendTime());
//                ImDB.getInstance(mContext).updateMessages(messageTab);
//                hasMessageTab = true;
//            }
//        }
//        if (!hasMessageTab) {
//            MessageTabEntity messageTab = new MessageTabEntity();
//            messageTab.setContent(chat.getContent());
//            messageTab.setMessageType(MessageTabEntity.FRIEND_MESSAGE);
//            messageTab.setName(tran.getSendName());
//            messageTab.setSenderId(senderId);
//            messageTab.setSendTime(chat.getSendTime());
//            messageTab.setUnReadCount(1);
//            mMessageEntities.add(messageTab);
//            ImDB.getInstance(mContext).saveMessage(messageTab);
//        }
//        chat.setMessageType(ChatEntity.RECEIVE);
//        List<ChatEntity> chatList = mChatMessagesMap.get(chat.getSenderId());
//        if (chatList == null) {
//            chatList = ImDB.getInstance(mContext).getChatMessage(
//                    chat.getSenderId());
//            getChatMessagesMap().put(chat.getSenderId(), chatList);
//        }
//        chatList.add(chat);
//        ImDB.getInstance(mContext).saveChatMessage(chat);
//        if (messageHandler != null) {
//            Message message = new Message();
//            message.what = 1;
//            messageHandler.sendMessage(message);
//        }
//        if (chatMessageHandler != null) {
//            Message message = new Message();
//            message.what = 1;
//            chatMessageHandler.sendMessage(message);
//        }
//    }
//
//    public Bitmap getUserPhoto() {
//        return mUserPhoto;
//    }
//
//    public void setUserPhoto(Bitmap mUserPhoto) {
//        this.mUserPhoto = mUserPhoto;
//    }
//
//    public List<MessageTabEntity> getMessageEntities() {
//        return mMessageEntities;
//    }
//
//    public void setMessageEntities(List<MessageTabEntity> mMessageEntities) {
//        this.mMessageEntities = mMessageEntities;
//    }
//
//    public void setMessageHandler(Handler handler) {
//        this.messageHandler = handler;
//    }
//
//    public void setChatHandler(Handler handler) {
//        this.chatMessageHandler = handler;
//    }
//
//    public void setfriendListHandler(Handler handler) {
//        this.friendListHandler = handler;
//    }

    /** 其它帮助类方法 */

    // 是否有该好友
    public boolean hasFriend(Integer account) {
        List<User> userList = getFriendList();
        for(User user : userList) {
            if(account == user.getAccount())
                return true;
        }
        return false;
    }


    // 是否加了该群
    /** 需改善 **/
    public boolean hasGroup(Integer account) {
        // 待完善
        List<User> groupList = getFriendList();
        for(User group : groupList) {
            if(account == group.getAccount())
                return true;
        }
        return false;
    }
}
