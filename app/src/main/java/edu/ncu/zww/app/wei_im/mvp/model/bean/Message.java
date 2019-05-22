package edu.ncu.zww.app.wei_im.mvp.model.bean;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;

/**
 * 聊天的消息类
 * */
public class Message extends RealmObject implements IMessage,Serializable {
            /*,
        MessageContentType.Image*//*, *//**//*this is for default image messages implementation*//**//*
        MessageContentType *//**//*and this one is for custom content type (in this case - voice message)*/
    private String id; // uuid
    private Integer chatType; // 聊天对象类型。
    private Integer groupId; // 群id；如果不是群聊天则不管
    private Date createdAt; // 创建时间
    private Contact user; // 信息发送方的信息
    private Integer receiveId; // 接收者账号
    private String sendStatus; // 发送状态
    private String msgType = MsgType.TEXT; // 消息类型MsgType,默认文本类型
    private String text; // 消息文本
    private ImgMsgBody image;
    private Object Annex; // 附件
    private Integer read = 0; // 已读标志。0未读，1已读。
//    private Voice voice;

    public Message(){}

    public Message(String id, Contact user, String text) {
        this(id, user, text, new Date());
    }

    public Message(String id, Contact user, String text, Date createdAt) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public Contact getUser() {
        return this.user;
    }

    public ImgMsgBody getImage() {
        return image;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
        // 非文本类型处理
        if (!msgType.equals(MsgType.TEXT)) {
            this.text = MsgType.typeToText(msgType);
        }
    }

    public void setUser(Contact user) {
        this.user = user;
    }

    public Integer getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Integer receiveId) {
        this.receiveId = receiveId;
    }

//    public Voice getVoice() {
//        return voice;
//    }

    public String getStatus() {
        return this.sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setImage(ImgMsgBody image) {
        this.image = image;
        setMsgType(MsgType.IMAGE);
    }

    public Object getAnnex() {
        return Annex;
    }

    public void setAnnex(Object annex) {
        Annex = annex;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    //    public void setVoice(Voice voice) {
//        this.voice = voice;
//    }

    /*
    public static class Voice {

        private String url;
        private int duration;

        public Voice(String url, int duration) {
            this.url = url;
            this.duration = duration;
        }

        public String getUrl() {
            return url;
        }

        public int getDuration() {
            return duration;
        }
    }*/

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", chatType=" + chatType +
                ", groupId=" + groupId +
                ", createdAt=" + createdAt +
                ", user=" + user +
                ", receiveId=" + receiveId +
                ", sendStatus='" + sendStatus + '\'' +
                ", msgType='" + msgType + '\'' +
                ", text='" + text + '\'' +
                ", image=" + image +
                ", Annex=" + Annex +
                '}';
    }
}
