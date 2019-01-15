package edu.ncu.zww.app.wei_im.presenter;

import android.text.TextUtils;

import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.contract.LoginContract;
import edu.ncu.zww.app.wei_im.model.db.LoginModelImpl;

public class LoginPresenter extends BasePresenter<LoginContract.LoginView> {

    private LoginModelImpl mLoginModel;

    public LoginPresenter() {
        mLoginModel = new LoginModelImpl();
    }

    public void checkFormat(String name, String password) {
        if (TextUtils.isEmpty(name)) {
            getView().onCheckFormatFail("请输入账号");
        } else if (TextUtils.isEmpty(password)) {
            getView().onCheckFormatFail("请输入密码");
        } else if (password.length() < 6 || password.length() > 18) {
            getView().onCheckFormatFail("密码格式不正确");
        } else {
            // getView().onCheckFormatSuccess();
            login(name, password);
        }
    }

    public void login(String name, String password) {
        mLoginModel.login(name, password, new LoginContract.LoginCallBack() {
            @Override
            public void onSuccess() {

                getView().onLoginSuccess();
                //System.out.println("xx");
            }

            @Override
            public void onFail(String errorInfo) {
                getView().onLoginFail(errorInfo);
            }
        });
    }
}
