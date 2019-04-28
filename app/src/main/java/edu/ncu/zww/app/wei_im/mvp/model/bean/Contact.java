package edu.ncu.zww.app.wei_im.mvp.model.bean;

import java.io.Serializable;
import java.util.Date;

import edu.ncu.zww.app.wei_im.utils.PinYinUtils;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


/**
 * 包装recycler的item.
 *
 * 该类还作为本地数据库联系人表实体类，在/main/assets/litepal注册映射模型
 * 作为联系人表，存放好友、非好友（群里的）
 * */
public class Contact extends RealmObject implements Serializable {

    @Ignore
    private static final long serialVersionUID = 1L;

    @PrimaryKey
    private Integer account; // 账号。唯一标识

    @Required
    private String name; // 名字。不为空.

    private String nick; // 备注

    private String img; // 图像地址

    private int isContact; // 是否为好友，0否1是

    private int sex;    // 0 男性，1 女性

    private Date data; // 时间

    private String pinyin;  // 拼音

    private String letter; // 所属于的字母类(拼音首字母)

    // 构造参数可改，测试用

    public Contact() {}

    public Contact(String name, Integer isContact) {
        this.name = name;
        this.isContact = isContact;
        if (isContact==1) {
            this.pinyin = PinYinUtils.getPinyin(name);
            this.letter = PinYinUtils.getLetter(pinyin);
        }
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getIsContact() {
        return isContact;
    }

    public void setIsContact(Integer isContact) {
        this.isContact = isContact;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getLetter() {
        return letter;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "account=" + account +
                ", name='" + name + '\'' +
                ", nick='" + nick + '\'' +
                ", img='" + img + '\'' +
                ", isContact=" + isContact +
                ", sex=" + sex +
                ", data=" + data +
                ", pinyin='" + pinyin + '\'' +
                ", letter='" + letter + '\'' +
                '}';
    }
}
