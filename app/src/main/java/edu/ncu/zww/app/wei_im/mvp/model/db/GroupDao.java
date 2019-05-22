package edu.ncu.zww.app.wei_im.mvp.model.db;

import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupInfo;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupMember;
import io.realm.Realm;
import io.realm.RealmResults;

public class GroupDao {

    private Realm realm;

    public GroupDao() {
        realm = Realm.getDefaultInstance();
    }

    public GroupInfo getGroupInfo(int gid) {
        GroupInfo groupInfo = realm.where(GroupInfo.class).equalTo("gid",gid).findFirst();
        return realm.copyFromRealm(groupInfo);
    }

    //  获取该群的所有成员
    public List<Contact> getMemberList(int gId) {
        GroupMember groupMember = realm.where(GroupMember.class)
                .equalTo("gid",gId).findFirst();
        return realm.copyFromRealm(groupMember).getMemberList();
    }

    //  获取该群的所有成员(在子线程)
    public List<Contact> getMemberListSync(int gId) {
        Realm mRealm = Realm.getDefaultInstance();
        GroupMember groupMemberRealm = mRealm.where(GroupMember.class)
                .equalTo("gid",gId).findFirst();
        if (groupMemberRealm == null) {
            mRealm.close();
            return null;
        }
        GroupMember groupMember = mRealm.copyFromRealm(groupMemberRealm);
        mRealm.close();
        return groupMember.getMemberList();
    }

    // 保存群成员信息
    public void saveMemberList(final GroupMember groupMember) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(groupMember);
            }
        });
    }

    // 保存群成员信息(子线程)
    public void saveMemberListSync(final GroupMember groupMember) {
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(groupMember);
            }
        });
        mRealm.close();
    }

    // 退出群聊
    public void quitGroup(final int gid) {
        // 先找出群的相关数据
        final RealmResults<GroupInfo> groupInfo = realm.where(GroupInfo.class)
                .equalTo("gid",gid).findAll();
        final GroupMember groupMember = realm.where(GroupMember.class)
                .equalTo("gid",gid).findFirst();

        // 进行删除
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                groupInfo.deleteFirstFromRealm(); // 删除群信息
                groupMember.setMemberList(null) ; // 删除群成员的成员连接
            }
        });
    }

    // 添加成员
    public void addMember(final int gid, final Contact contact) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                GroupMember groupMember = realm.where(GroupMember.class)
                        .equalTo("gid",gid).findFirst();
                groupMember.getMemberList().add(contact);
            }
        });
    }

    // 移除成员
    public void removeMember(final int gid, final int position) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                GroupMember groupMember = realm.where(GroupMember.class)
                        .equalTo("gid",gid).findFirst();
                groupMember.getMemberList().remove(position);
            }
        });
    }



    public void closeRealm() {
        if (realm!=null) {
            realm.close();
        }
    }


}
