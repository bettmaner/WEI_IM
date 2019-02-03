package edu.ncu.zww.app.wei_im.client;

import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;

/**
 * 消息监听接口
 */
public interface MessageListener {
    public void Message(TranObject msg);
}
