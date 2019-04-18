package edu.ncu.zww.app.wei_im.mvp.model.bean;

import java.io.Serializable;
import java.util.Date;

import edu.ncu.zww.app.wei_im.utils.PinYinUtils;

/**
 * 包装recycler的item.
 * 也可以作为好友或群用于传输的名片
 * */
public class Contact {
    private String name; // 昵称
    private Integer account; // 账号
    private String img; // 图像地址
    private int sex;    // 0 男性，1 女性
    private String content; // 内容
    private Date data; // 时间
    private Integer type; //类型，0人1群

    private String pinyin;  // 拼音
    private String letter; // 所属于的字母类(拼音首字母)


    // 构造参数可改，测试用

    public Contact() {}

    public Contact(String name) {
        this.name = name;
        setNamePinyin();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setNamePinyin() {
        if (name != null && name.length() > 0 ) {
            this.pinyin = PinYinUtils.getPinyin(name);
            this.letter = PinYinUtils.getLetter(pinyin);
        }
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getLetter() {
        return letter;
    }
}
