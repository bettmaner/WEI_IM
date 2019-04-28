package edu.ncu.zww.app.wei_im.mvp.contract;

import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Invitation;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ResultBean;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.bean.User;
import io.reactivex.Observable;

/**
 * 契约类
 *
 * 规范定义，定义功能和模板。主要是定义回调类
 *
 */
public class OperaFGContract {

    /**
     *  view
     *  作用：view层得到要显示的数据
     */
    public interface BaseView {
        void onQuerySuccess(List list);
        void onFail(String info);
        void onError(String info);
    }

    // 添加好友层的视图接口
    public interface AddContactView extends BaseView{
        void onAddSuccess(String info);
    }

    // 好友邀请状态界面的视图接口
    public interface NewContactView {
        void accessSuccess(ResultBean result);
        void accessFail(ResultBean result);
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
        Observable<String> addContact(Contact contact,String info, Integer type);
    }

    public interface NewContactModel {
        // 查找邀请数据列表
        List<Invitation> queryInvitations();

        // 接受好友请求
        Observable<ResultBean> accessInvitation(Invitation invitation);

        // 更改邀请状态
        void updateInvitation(Invitation invitation);

    }

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
