package edu.ncu.zww.app.wei_im.mvp.model.impl;

import edu.ncu.zww.app.wei_im.client.Client;
import edu.ncu.zww.app.wei_im.client.ClientControl;
import edu.ncu.zww.app.wei_im.mvp.contract.ChatContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Message;
import edu.ncu.zww.app.wei_im.mvp.model.bean.MsgSendStatus;
import edu.ncu.zww.app.wei_im.mvp.model.bean.User;
import edu.ncu.zww.app.wei_im.mvp.model.db.ChatDao;
import edu.ncu.zww.app.wei_im.utils.BeanTransfer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class ChatModelImpl implements ChatContract.ChatModel {

    private ChatDao chatDao;
    private User user;

    public ChatModelImpl() {
        this.chatDao = new ChatDao();
        user = ApplicationData.getInstance().getUserInfo();
    }

    public Contact getContact(int account) {
        return chatDao.getContact(account);
    }

    public Contact getUser() {
        return BeanTransfer.userToContact(user);
    }

    @Override
    public Observable<Message> sendMsg(final Message message) {
        return Observable.create(new ObservableOnSubscribe<Message>() {
            @Override
            public void subscribe(ObservableEmitter<Message> emitter) throws Exception {
                ClientControl.sendMsg(message);
                // 如果成功
                if (Client.getInstance().isConnected()) {
                    message.setSendStatus(MsgSendStatus.SENT);
                } else {
                    message.setSendStatus(MsgSendStatus.FAILED);
                }
                emitter.onNext(message);
                emitter.onComplete();
            }
        });
    }


    public void closeRealm() {
        chatDao.closeRealm();
    }
}
