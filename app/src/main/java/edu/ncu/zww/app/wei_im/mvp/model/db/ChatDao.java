package edu.ncu.zww.app.wei_im.mvp.model.db;

import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import io.realm.Realm;

public class ChatDao {

    private Realm realm;

    public ChatDao() {
        realm = Realm.getDefaultInstance();
    }

    public Contact getContact(Integer account) {
        Contact contactRealm = realm.where(Contact.class).equalTo("account",account).findFirst();
        return realm.copyFromRealm(contactRealm);
    }

    public void closeRealm() {
        if (realm!=null) {
            realm.close();
        }
    }
}
