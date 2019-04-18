package edu.ncu.zww.app.wei_im.mvp.contract;

import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.bean.User;
import io.reactivex.Observable;

/**
 * 契约类
 * 添加好友/群 创建群聊的契约类
 * 规范定义，定义功能和模板。主要是定义回调类
 *
 */
public class OperaFGContract {

    /**
     *  view
     *  作用：view层得到要显示的数据
     */
    public interface AddContactView {
        void onQuerySuccess(List list);
        void onAddSuccess(String info);
        void onAddFail(String info);
        void onError(String info);
    }


    /**
     *  model
     *  作用：model层根据参数向网络获取数据,并通过callBack回调给presenter
     */
    public interface AddContactModel {
        // 查找用户
        Observable<List> queryContacts(String account, Integer type);
        // 添加用户
        Observable<TranObject> addContact(Integer account, Integer type);
    }
    /*public interface AddModel {
        void Operate(int id)
    }*/


    /**
     *  CallBack
     *  作用：model层使用CallBack方法回调给presenter需要的数据
     */
//    public interface QueryCallBack {
//        void onQueryData(List list);
//    }
//
//    public interface AddCallBack {
//        void onAddSuccess(String info);
//        void onAddFail(String info);
//    }
}
