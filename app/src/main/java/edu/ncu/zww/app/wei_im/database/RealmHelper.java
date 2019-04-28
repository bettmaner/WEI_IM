package edu.ncu.zww.app.wei_im.database;

import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Invitation;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TestBean;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmHelper {
    private static RealmHelper mRealmHelper;

//    private Realm mRealm; // realm数据库

    private RealmHelper() {}

    public static RealmHelper getInstance() {
        if (mRealmHelper==null) {
            mRealmHelper = new RealmHelper();
        }
        return mRealmHelper;
    }

    // 根据账号选择数据库
    public void initDB(String account) {
//        if (mRealm!=null && !mRealm.isClosed()) {
//            mRealm.close();
//        }
        RealmConfiguration mConfig = new RealmConfiguration.Builder()
                .name("im"+account+".realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(mConfig);
//        mRealm = Realm.getInstance(mConfig);
        System.out.println("实例realm在线程"+Thread.currentThread().getName());
    }


    // 保存联系人
    public void saveContacts(final List<Contact> contacts) {
        System.out.println("保存realm"+Thread.currentThread().getName());
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(contacts);//传入对象
            }
        });
        mRealm.close();
    }

    public List<Contact> getFriends() {
        Realm mRealm = Realm.getDefaultInstance();
//        RealmResults<Contact> friends = mRealm.where(Contact.class).findAll().sort("id", Sort.ASCENDING);
        RealmResults<Contact> friends = mRealm.where(Contact.class).equalTo("isContact",1).findAll();
        //return mRealm.copyFromRealm(friends);
        List<Contact> friendList = mRealm.copyFromRealm(friends);
        mRealm.close();
        return friendList;
    }

    // 保存邀请信息
    public void saveInvitation(final Invitation invitation) {
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(invitation);//传入对象
            }
        });
        mRealm.close();
    }

    // 获取邀请列表
    public List<Invitation> getInvitationList() {
        Realm mRealm = Realm.getDefaultInstance();
        // 结果按时间降序
        RealmResults<Invitation> invitations =  mRealm.where(Invitation.class).findAll().sort("createDate",Sort.DESCENDING);
        List<Invitation> invitationList = mRealm.copyFromRealm(invitations);
        mRealm.close();
        return invitationList;
    }

    // 保存邀请信息
    public void saveTestBean(final TestBean testBean) {
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(testBean);//传入对象
            }
        });
        mRealm.close();
//        Realm mRealm = Realm.getDefaultInstance();

    }
}