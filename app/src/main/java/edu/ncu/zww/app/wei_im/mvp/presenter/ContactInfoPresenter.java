package edu.ncu.zww.app.wei_im.mvp.presenter;

import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.client.Client;
import edu.ncu.zww.app.wei_im.mvp.contract.ContactInfoContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.impl.ContactInfoModelImpl;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactInfoPresenter
        extends BasePresenter<ContactInfoContract.ContactInfoView> {

    private ContactInfoModelImpl mInfoModel;

    public ContactInfoPresenter() {
        this.mInfoModel = new ContactInfoModelImpl();
    }

    @Override
    public void getMessage(TranObject msg) {

    }

    // 添加联系人之前的验证
    public void preAddContact(Contact contact,String info) {
        if (!Client.getInstance().isConnected()) {
            getView().onError("未连接服务器");
            return;
        }
        // 加人的情况
        ApplicationData appData = ApplicationData.getInstance();
        Integer account = contact.getAccount();
        if (mInfoModel.hasInvitation(account)) {
            getView().onFail("已经有该邀请了");
            return;
        }

        // 通过前面验证后开始添加
        addContact(contact,info);
    }

    public void addContact(Contact contact, String info) {
        mInfoModel.addContact(contact,info)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String info) {
                        getView().onAddSuccess("好友申请已发送，等待好友确认");
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void close() {
        mInfoModel.closeRealm();
    }

}
