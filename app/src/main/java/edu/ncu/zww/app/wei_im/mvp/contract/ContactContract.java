package edu.ncu.zww.app.wei_im.mvp.contract;

import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupInfo;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ResultBean;
import io.reactivex.Observable;

public class ContactContract {

    /**
     *  view
     *  作用：view层得到要显示的数据
     */
    public interface ContactView {
        void onQuerySuccess(List list);
        void onError(String info);
    }

    public interface SelectorView {
        void onCreatedSuccess(GroupInfo groupInfo);
        void onCreatedFail(ResultBean result);
        void onSubmitVailOk();
        void onLoading();
        void onError(String info);
    }

    /**
     *  model
     *  作用：model层根据参数向网络获取数据,并通过callBack回调给presenter
     */
    public interface ContactModel {
        // 查找好友
        Observable<List<Contact>> queryFriends();
    }

    public interface SelectorModel {

    }

    public interface GroupModel {
        // 查找群
        Observable<List<GroupInfo>> queryGroupList();
    }
}
