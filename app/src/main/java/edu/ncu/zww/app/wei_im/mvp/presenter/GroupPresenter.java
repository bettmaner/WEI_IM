package edu.ncu.zww.app.wei_im.mvp.presenter;

import java.util.List;

import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.mvp.contract.ContactContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupInfo;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.impl.GroupModelImpl;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GroupPresenter extends BasePresenter<ContactContract.ContactView> {

    private GroupModelImpl mGroupModel;

    public GroupPresenter() {
        this.mGroupModel = new GroupModelImpl();
    }

    @Override
    public void getMessage(TranObject msg) {

    }

    public void getGroupList() {
         mGroupModel.queryGroupList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GroupInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<GroupInfo> groupInfoList) {
                        getView().onQuerySuccess(groupInfoList);
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

    }
}
