package edu.ncu.zww.app.wei_im.mvp.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

// 群信息
public class GroupInfo extends RealmObject implements Serializable {

    @Ignore
    private static final long serialVersionUID = 1L;

    @PrimaryKey
    private Integer gid; // 账号。唯一标识

    private String name; // 名字。不为空，默认unknown

    private String avatar; // 群头像

    private Integer creator; // 群主

    private Date createTime; // 创建时间
    @Ignore
    private List<Integer> memberList; // 群成员账号


    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public List<Integer> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Integer> memberList) {
        this.memberList = memberList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "GroupInfo{" +
                "gid=" + gid +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", memberList=" + memberList +
                '}';
    }
}
