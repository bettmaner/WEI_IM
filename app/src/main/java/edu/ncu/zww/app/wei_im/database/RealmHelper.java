package edu.ncu.zww.app.wei_im.database;

import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmHelper {
    private static RealmHelper mRealmHelper;

    private Realm mRealm; // realm数据库

    private RealmHelper() {}

    public static RealmHelper getInstance() {
        if (mRealmHelper==null) {
            mRealmHelper = new RealmHelper();
        }
        return mRealmHelper;
    }

    // 根据账号选择数据库
    public void initDB(String account) {
        if (mRealm!=null && !mRealm.isClosed()) {
            mRealm.close();
        }
        RealmConfiguration mConfig = new RealmConfiguration.Builder()
                .name("im"+account+".realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        mRealm = Realm.getInstance(mConfig);
    }


    public void saveContacts(List<Contact> contacts) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(contacts);//传入对象
        //mRealm.insertOrUpdate(contacts);
        mRealm.commitTransaction();
    }
    public List<Contact> getFriends() {
//        RealmResults<Contact> friends = mRealm.where(Contact.class).findAll().sort("id", Sort.ASCENDING);
        RealmResults<Contact> friends = mRealm.where(Contact.class).equalTo("isContact",1).findAll();
        return mRealm.copyFromRealm(friends);
    }
}
