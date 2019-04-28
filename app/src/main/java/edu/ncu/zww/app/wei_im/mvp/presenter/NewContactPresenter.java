package edu.ncu.zww.app.wei_im.mvp.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.mvp.contract.OperaFGContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Invitation;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ResultBean;
import edu.ncu.zww.app.wei_im.mvp.model.bean.StatusText;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.impl.NewContactModelImpl;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewContactPresenter extends BasePresenter<OperaFGContract.NewContactView> {

    private NewContactModelImpl mNewContactModel;


    public NewContactPresenter() {
        this.mNewContactModel = new NewContactModelImpl();
    }

    @Override
    public void getMessage(TranObject msg) {

    }

    public List<Invitation> queryInvitations() {
        return mNewContactModel.queryInvitations();
    }

    public void accessInvitation(final Invitation invitation) {

//        invitation.setStatus(StatusText.CONTACT_AGREE);
        invitation.setStatus(StatusText.CONTACT_WAIT);
        mNewContactModel.accessInvitation(invitation)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean result) {
                        if (result.isSuccess()) {
                            mNewContactModel.updateInvitation(invitation);
                            getView().accessSuccess(result);
                        } else {
                            getView().accessFail(result);
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

    public void closeRealm() {
        mNewContactModel.closeRealm();
    }
}
