package edu.ncu.zww.app.wei_im.mvp.contract;


public class MemberContract {

    /**
     *  view
     *  作用：view层得到要显示的数据
     */
    public interface MemberView {
    }

    /**
     *  model
     *  作用：model层根据参数向网络获取数据,并通过callBack回调给presenter
     */
    public interface MemberModel {
        // 查找好友
    }

}
