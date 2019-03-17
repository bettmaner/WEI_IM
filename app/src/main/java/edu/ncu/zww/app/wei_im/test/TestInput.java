package edu.ncu.zww.app.wei_im.test;

import edu.ncu.zww.app.wei_im.client.MessageListener;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType;
import edu.ncu.zww.app.wei_im.mvp.model.bean.User;
/*
*  使用：RegisterActivity、GetMsgService
* */
public class TestInput {

    private static TestInput testInput = null;

    private TestInput(){}

    private MessageListener messageListener;// 消息监听接口对象

    public static TestInput getInstance() {
        if (testInput == null) {
            testInput = new TestInput();
        }
        return testInput;
    }

    public boolean start() {
        return true;
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void setMsg() {
        TranObject<User> o = new TranObject<User>(TranObjectType.LOGIN);
        User u = new User();
        u.setId(1212);
        //u.setPassword(Encode.getEncode("MD5", password));
        o.setObject(u);
        System.out.println("TestInput里的消息"+o);
        messageListener.Message(o);
    }
}
