package edu.ncu.zww.app.wei_im.mvp.model.bean;

import org.litepal.annotation.Column;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

// 群信息
public class GroupInfo extends RealmObject implements Serializable {

    @Ignore
    private static final long serialVersionUID = 1L;

    private Integer gid; // 账号。唯一标识

    private String name; // 名字。不为空，默认unknown

    private String img; // 群头像

    private String Creator; // 群主

//    private List<Contact> member;



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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    @Override
    public String toString() {
        return "Group{" +
                "gid=" + gid +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", Creator='" + Creator + '\'' +
                '}';
    }
}
