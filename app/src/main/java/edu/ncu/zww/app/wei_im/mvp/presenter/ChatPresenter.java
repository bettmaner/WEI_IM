package edu.ncu.zww.app.wei_im.mvp.presenter;

import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.mvp.contract.ChatContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Message;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ResultBean;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.impl.ChatModelImpl;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChatPresenter extends BasePresenter<ChatContract.ChatView> {

    private ChatModelImpl mChatModel;
    private int chatType,chatId;

    public ChatPresenter(int chatType,int chatId) {
        this.mChatModel = new ChatModelImpl();
        this.chatType = chatType;
        this.chatId = chatId;
    }

    @Override
    public void getMessage(TranObject msg) { // 广播接收的消息
        System.out.println("ds");
        Message message = (Message) msg.getObject();
        // 该消息是否是当前聊天的
        if (message.getChatType() + chatType == 0 && message.getSenderId() == chatId) { // 私人聊天情况判断
            getView().onReceiveMsg(message);
        } else if (message.getChatType() + chatType == 2 && message.getGroupId() == chatId) { // 群聊天情况
            getView().onReceiveMsg(message);
        }
    }

    public Contact getUser() {
        return mChatModel.getUser();
    }

    public Contact getContact(int account) {
        return mChatModel.getContact(account);
    }

    public void sendMessage(Message message) {
        mChatModel.sendMsg(message)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Message>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Message message1) {
                        getView().onSendResult(message1);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public void close() {
        mChatModel.closeRealm();
    }
}
