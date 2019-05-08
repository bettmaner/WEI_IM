package edu.ncu.zww.app.wei_im.mvp.contract;

import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Message;
import io.reactivex.Observable;


public class MsgContract {

    /**
     *  view
     *  作用：view层得到要显示的数据
     */
    public interface MsgView {
        //void getMsgList(List<Message> list);
    }


    /**
     *  model
     *  作用：model层根据参数向网络获取数据,并通过callBack回调给presenter
     */
    public interface MsgModel {

        // 获取消息列表
        List<Message> getMsgList();
        // 查找好友
//        Observable<List<Contact>> queryFriends();
    }

}
