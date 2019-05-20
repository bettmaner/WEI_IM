package edu.ncu.zww.app.wei_im.mvp.presenter;

import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.mvp.contract.ChatContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.impl.ChatModelImpl;

public class ChatPresenter extends BasePresenter<ChatContract.ChatView> {

    private ChatModelImpl mChatModel;

    public ChatPresenter() {
        this.mChatModel = new ChatModelImpl();
    }

    @Override
    public void getMessage(TranObject msg) {

    }

    public Contact getContact(int account) {
        return mChatModel.getContact(account);
    }

    @Override
    public void close() {
        mChatModel.closeRealm();
    }
}
