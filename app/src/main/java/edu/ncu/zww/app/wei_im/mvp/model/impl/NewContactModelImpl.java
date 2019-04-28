package edu.ncu.zww.app.wei_im.mvp.model.impl;

import java.util.List;

import edu.ncu.zww.app.wei_im.database.RealmHelper;
import edu.ncu.zww.app.wei_im.mvp.contract.OperaFGContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Invitation;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ResultBean;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.Sort;

public class NewContactModelImpl implements OperaFGContract.NewContactModel {

    private Realm realm;

    public NewContactModelImpl() {
        realm = Realm.getDefaultInstance();
    }


    @Override
    public List<Invitation> queryInvitations() {
        List<Invitation> invitations = realm.where(Invitation.class).findAll().sort("createDate",Sort.DESCENDING);
        List<Invitation> invitationsList = realm.copyFromRealm(invitations);
        return invitationsList;
    }

    @Override
    public Observable<ResultBean> accessInvitation(Invitation invitation) {

        return Observable.create(new ObservableOnSubscribe<ResultBean>() {
            @Override
            public void subscribe(ObservableEmitter<ResultBean> emitter) throws Exception {
                // 给客户发消息
                // 结果返回给presenter
                ResultBean result = new ResultBean(true,"添加成功");
                emitter.onNext(result);
            }
        });

    }

    @Override
    public void updateInvitation(final Invitation invitation) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(invitation);
            }
        });
    }

    public void closeRealm() {
        if (realm!=null) {
            realm.close();
        }
    }

}
