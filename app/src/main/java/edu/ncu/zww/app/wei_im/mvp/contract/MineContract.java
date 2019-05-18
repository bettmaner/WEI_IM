package edu.ncu.zww.app.wei_im.mvp.contract;


import io.reactivex.Observable;

/**
 * 契约类
 * 我的界面 的契约类
 * 规范定义，定义功能和模板。主要是定义回调类
 *
 */
public class MineContract {

    /**
     *  view
     *  作用：view层得到要显示的数据
     */
    public  interface MineView {
//        void updateSuccess(String info);
//        void updateFail(String info);
        void error(String info);
        void showDoing(Integer type); // 弹出等待框
        void success(String info);
        void fail(String info);
    }

    /**
     *  model
     *  作用：model层根据参数向网络获取数据,并通过callBack回调给presenter
     */
    public interface MineModel {
        /**
         * 泛型为返回给present的数据类型.
         * 参数为presenter传给model的数据
         **/
        // 修改资料，
//        Observable<String> queryContacts(User user);


        // 退出登录
        Observable<String> logOff();
        // 关闭应用
        Observable<String> closeApp();
    }
}
