package edu.ncu.zww.app.wei_im.mvp.model.db;

import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupInfo;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Message;
import io.realm.Realm;

public class MessageDao {

    private Realm realm;

    public MessageDao() {
        realm = Realm.getDefaultInstance();
    }

    // 保存信息
    public void insertMessage(Message msg) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

            }
        });

    }

    // 获取群信息
    public GroupInfo getGroupInfo (int gid) {
        GroupInfo groupInfo =  realm.where(GroupInfo.class).equalTo("gid",gid).findFirst();
        return groupInfo;
    }

    public void closeRealm() {
        if (realm!=null) {
            realm.close();
        }
    }
}
