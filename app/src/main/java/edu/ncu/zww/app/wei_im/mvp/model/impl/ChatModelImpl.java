package edu.ncu.zww.app.wei_im.mvp.model.impl;

import edu.ncu.zww.app.wei_im.mvp.contract.ChatContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ResultBean;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.db.ChatDao;
import io.reactivex.Observable;
import io.realm.Realm;

public class ChatModelImpl implements ChatContract.ChattModel {

    private ChatDao chatDao;

    public ChatModelImpl() {
        this.chatDao = new ChatDao();
    }

    public Contact getContact(int account) {
        return chatDao.getContact(account);
    }


    @Override
    public Observable<ResultBean> sendMsg(TranObject tranObj) {
        return null;
    }


    public void closeRealm() {
        chatDao.closeRealm();
    }
}
