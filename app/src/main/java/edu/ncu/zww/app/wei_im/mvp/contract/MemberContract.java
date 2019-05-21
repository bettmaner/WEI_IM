package edu.ncu.zww.app.wei_im.mvp.contract;


import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupInfo;
import io.reactivex.Observable;

public class MemberContract {

    /**
     *  view
     *  作用：view层得到要显示的数据
     */
    public interface MemberView {
        void onQuerySuccess(List<Contact> list);
        void quitGroupSuccess();
    }


    /**
     *  model
     *  作用：model层根据参数向网络获取数据,并通过callBack回调给presenter
     */
    public interface MemberModel {
        // 获取群基本信息
        GroupInfo getGroupInfo(int gid);
        // 查找成员
        Observable<List<Contact>> queryMemberList(int gid);
    }

}
