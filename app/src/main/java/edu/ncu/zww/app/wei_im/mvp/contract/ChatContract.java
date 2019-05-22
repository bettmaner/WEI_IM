package edu.ncu.zww.app.wei_im.mvp.contract;

import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Message;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ResultBean;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import io.reactivex.Observable;

public class ChatContract {

    /**
     *  view
     *  作用：view层得到要显示的数据
     */
    public interface ChatView {
        void onSendResult(Message message);
        void onError(String info);
    }


    /**
     *  model
     *  作用：model层根据参数向网络获取数据,并通过callBack回调给presenter
     */
    public interface ChatModel {
        // 发送消息
        Observable<Message> sendMsg(Message message);
    }
}
