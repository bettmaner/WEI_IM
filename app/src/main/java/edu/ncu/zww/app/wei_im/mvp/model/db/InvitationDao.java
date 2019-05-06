package edu.ncu.zww.app.wei_im.mvp.model.db;

import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.model.bean.Invitation;
import io.realm.Realm;
import io.realm.Sort;

public class InvitationDao {
    private Realm realm;

    public InvitationDao() {
        realm = Realm.getDefaultInstance();
    }

    // 查询所有的invitation
    public List<Invitation> queryInvitations() {
        List<Invitation> invitations = realm.where(Invitation.class).findAll().sort("createDate",Sort.DESCENDING);
        // 使用copyFromRealm方法将结果转化为平常可操作的实体类
        List<Invitation> invitationsList = realm.copyFromRealm(invitations);
        return invitationsList;
    }

    // 更改invitation
    public void updateInvitation(final Invitation invitation) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(invitation);
            }
        });
    }

    public boolean hasInvitation(Integer account) {
        List<Invitation> invitations = realm.where(Invitation.class).equalTo("account",account).findAll();
        if (invitations.size()>0) {
            return true;
        } else {
            return false;
        }
    }

    public void closeRealm() {
        if (realm!=null) {
            realm.close();
        }
    }
}
