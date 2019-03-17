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
        void onLRSuccess(String info);
        void onLRFail(String errorInfo);
        void NetWorkAvailable();
        void NetWorkUnAvailable();
    }

    public interface LoginModel{
        void login(Integer account, String password, LRCallBack callBack);
    }

    public interface RegisterModel{
        void register(String email, String password, LRCallBack callBack);
    }

    public interface LRCallBack{
        void onSuccess(String info);
        void onFail(String errorInfo);
    }
}
