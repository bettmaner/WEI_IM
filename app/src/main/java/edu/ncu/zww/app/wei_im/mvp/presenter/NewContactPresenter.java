package edu.ncu.zww.app.wei_im.mvp.presenter;

import java.util.List;

import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.mvp.contract.OperaFGContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Invitation;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ResultBean;
import edu.ncu.zww.app.wei_im.mvp.model.bean.StatusText;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.impl.NewContactModelImpl;
import io.reactivex.Observer;
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

    @Override
    public void close() {

    }

    public List<Invitation> queryInvitations() {
        return mNewContactModel.queryInvitations();
    }

    public void accessInvitation(final Invitation invitation) {

        // 邀请状态切换成已添加（但并未保存，待返回成功后才保存）
        invitation.setStatus(StatusText.CONTACT_AGREE);
//        invitation.setStatus(StatusText.CONTACT_WAIT);
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
