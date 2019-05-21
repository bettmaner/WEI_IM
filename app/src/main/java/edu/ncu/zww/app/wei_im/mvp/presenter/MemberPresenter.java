package edu.ncu.zww.app.wei_im.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.mvp.contract.MemberContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupInfo;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ResultBean;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.impl.MemberModelImpl;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MemberPresenter extends BasePresenter<MemberContract.MemberView> {

    private MemberModelImpl mMemberModel;

    public MemberPresenter() {
        this.mMemberModel = new MemberModelImpl();
    }

    @Override
    public void getMessage(TranObject msg) {

    }

    public void getMemberList(int gid) {
        mMemberModel.queryMemberList(gid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Contact>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Contact> memberList) {
                        getView().onQuerySuccess(memberList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public GroupInfo getGroupInfo(Integer gid) {
        return mMemberModel.getGroupInfo(gid);
    }

    public void quitGroup(final Integer gid) {

        Integer userAccount = ApplicationData.getInstance().getUserInfo().getAccount();
        final List<Integer> memberList = new ArrayList<>();
        memberList.add(userAccount);

        mMemberModel.cdGroupMember(gid, memberList,0)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        if (resultBean.isSuccess()) {
                            ApplicationData.getInstance().removeGroup(gid);
                            mMemberModel.quitGroupFromRealm(gid);
                            getView().quitGroupSuccess();
                        }
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
        mMemberModel.closeRealm();
    }
}
