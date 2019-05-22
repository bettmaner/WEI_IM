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

    public ChatPresenter() {
        this.mChatModel = new ChatModelImpl();
    }

    @Override
    public void getMessage(TranObject msg) {

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
