package edu.ncu.zww.app.wei_im.contract;

/* 契约类
 *
 * 规范定义，定义功能和模板。主要是定义回调类
 *
 */
public class LoginContract {

    public interface LoginView {
        //void onCheckFormatSuccess();
        void onCheckFormatFail(String info);
        void onLoginSuccess();
        void onLoginFail(String errorInfo);
    }

    public interface LoginModel{
        void login(String name,String password,LoginCallBack callBack);
    }

    public interface LoginCallBack{
        void onSuccess();
        void onFail(String errorInfo);
    }
}
