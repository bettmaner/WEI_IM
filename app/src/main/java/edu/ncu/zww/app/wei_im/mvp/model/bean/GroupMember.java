package edu.ncu.zww.app.wei_im.mvp.model.bean;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

// 群成员
public class GroupMember extends RealmObject {

    private int gid; // 群账号
    private RealmList<Contact> memberList; // 群成员

    public GroupMember() { // Realm要保证有无参构造
    }

    public GroupMember(int gid, RealmList<Contact> memberList) {
        this.gid = gid;
        this.memberList = memberList;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public RealmList<Contact> getMemberList() {
        return memberList;
    }

    public void setMemberList(RealmList<Contact> memberList) {
        this.memberList = memberList;
    }

    @Override
    public String toString() {
        return "GroupMember{" +
                "gid=" + gid +
                ", memberList=" + memberList +
                '}';
    }
}
