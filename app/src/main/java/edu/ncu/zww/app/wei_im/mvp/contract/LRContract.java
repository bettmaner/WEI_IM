package edu.ncu.zww.app.wei_im.mvp.contract;

/* 契约类
 *
 * 登录或注册的契约类
 * 规范定义，定义功能和模板。主要是定义回调类
 *
 */
public class LRContract {

    public interface LRView {
        //void onCheckFormatSuccess();
        void onCheckFormatFail(String info);
        void onLRSuccess();
        void onLRFail(String errorInfo);
        void NetWorkAvailable();
        void NetWorkUnAvailable();
    }

    public interface LRModel{
        void loginRegister(String account, String password, LRCallBack callBack);
    }

    public interface LRCallBack{
        void onSuccess();
        void onFail(String errorInfo);
    }
}
