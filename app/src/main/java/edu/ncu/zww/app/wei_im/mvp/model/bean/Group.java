package edu.ncu.zww.app.wei_im.mvp.model.bean;

import org.litepal.annotation.Column;

// 群信息
public class Group {

    @Column(nullable = false)
    private Integer gid; // 账号。唯一标识

    @Column(nullable = false, defaultValue = "unknown")
    private String name; // 名字。不为空，默认unknown

    private String Creator; // 群主

    private String member;

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

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }
}
