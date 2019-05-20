package edu.ncu.zww.app.wei_im.mvp.model.impl;

import java.util.Date;
import java.util.UUID;

import edu.ncu.zww.app.wei_im.client.ClientControl;
import edu.ncu.zww.app.wei_im.database.RealmHelper;
import edu.ncu.zww.app.wei_im.mvp.contract.ContactInfoContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Invitation;
import edu.ncu.zww.app.wei_im.mvp.model.bean.StatusText;
import edu.ncu.zww.app.wei_im.mvp.model.db.InvitationDao;
import edu.ncu.zww.app.wei_im.utils.BeanTransfer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType.FRIEND_REQUEST;

public class ContactInfoModelImpl implements ContactInfoContract.ContactInfoModel {

    private InvitationDao invitationDao;

    public ContactInfoModelImpl() {
        invitationDao = new InvitationDao();
    }

    @Override
    public Observable<Contact> getContact(Integer account) {
        return Observable.create(new ObservableOnSubscribe<Contact>() {
            @Override
            public void subscribe(ObservableEmitter<Contact> emitter) throws Exception {
                //emitter.onNext(contactList);
            }
        });
    }

    // 是否已存在该邀请
    public boolean hasInvitation(Integer account) {
        return invitationDao.hasInvitation(account);
    }

    public Observable<String> addContact(final Contact toUser, final String info) {
        return Observable.create(new ObservableOnSubscribe<String>(){

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                Contact fromUser = BeanTransfer.userToContact(ApplicationData.getInstance().getUserInfo());
                Invitation invitation = new Invitation();
                invitation.setUuid(UUID.randomUUID().toString());
                invitation.setToUser(toUser);
                invitation.setInfo(info);
                invitation.setCreateDate(new Date());
                invitation.setType(0);
                invitation.setStatus(StatusText.CONTACT_WAIT);
                ClientControl.sendConOrGroupRequest(invitation,FRIEND_REQUEST);
                RealmHelper.getInstance().saveInvitation(invitation);

                emitter.onNext("发送成功");
                emitter.onComplete();
            }

        });
    }

    public void closeRealm() {
        invitationDao.closeRealm();
    }

}
