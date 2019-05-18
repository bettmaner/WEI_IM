package edu.ncu.zww.app.wei_im.mvp.model.bean;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.util.Date;


public class Message implements IMessage/*,
        MessageContentType.Image*//*, *//**//*this is for default image messages implementation*//**//*
        MessageContentType *//**//*and this one is for custom content type (in this case - voice message)*/ {

    private String id; // uuid
    private String chatType; // 聊天对象类型。内容说明:私人聊天为‘c’+对方账号，群聊天为'g'+群账号。
    private String msgType = MsgType.TEXT; // 消息类型MsgType,默认文本类型
    private String text; // 消息文本
    private Date createdAt; // 创建时间
    private Contact user; // 对方（对本用户而言）信息
    private String senderId; // 发送者账号
    private String sendStatus; // 发送状态
    private ImgMsgBody image;
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

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
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

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
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
}
