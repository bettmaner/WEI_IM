package edu.ncu.zww.app.wei_im.mvp.presenter;

import java.util.List;

import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.client.Client;
import edu.ncu.zww.app.wei_im.mvp.contract.OperaFGContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.bean.User;
import edu.ncu.zww.app.wei_im.mvp.model.impl.AddContactModelImpl;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddContactPresenter extends BasePresenter<OperaFGContract.AddContactView> {

    private AddContactModelImpl mAddContactModel;

    public AddContactPresenter() {
        this.mAddContactModel = new AddContactModelImpl();
    }

    @Override
    public void getMessage(TranObject msg) {

    }

    public void queryContacts(String account,Integer type) {
        mAddContactModel.queryContacts(account,type)
                .subscribeOn(Schedulers.newThread()) // 观察者的操作线程，Schedulers.newThread()启动新线程。
                .observeOn(AndroidSchedulers.mainThread())  // 在UI线程中执行结果（观察者操作）
                .subscribe(new Observer<List>(){
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List list) {
                        getView().onQuerySuccess(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onAddFail("服务器连接异常");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    // 添加联系人之前的验证
    public void preAddContact(Integer account,Integer type) {
        if (!Client.getInstance().isConnected()) {
            getView().onError("未连接服务器");
            return;
        }
        // 加人的情况
        ApplicationData appData = ApplicationData.getInstance();
        if (type == 0) {
            if (account == appData.getUserInfo().getAccount()){
                getView().onAddFail("不能添加自己");
                return;
            } else if (appData.hasFriend(account)) {
                getView().onAddFail("已经有该好友了");
                return;
            }
        }
        if (type==1 && appData.hasGroup(account)) {
            getView().onAddFail("已经加过该群了");
            return;
        }
        // 通过前面验证后开始添加
        addContact(account,type);
    }

    // 发送联系人添加请求
    public void addContact(Integer account,Integer type) {
        mAddContactModel.addContact(account, type)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TranObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TranObject tranObject) {

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

}
