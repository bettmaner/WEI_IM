package edu.ncu.zww.app.wei_im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import edu.ncu.zww.app.wei_im.MApplication;
import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.mvp.contract.LRContract;
import edu.ncu.zww.app.wei_im.mvp.model.db.LoginModelImpl;
import edu.ncu.zww.app.wei_im.utils.NetWorkUtil;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;

public class LoginPresenter extends BasePresenter<LRContract.LRView> {

    private LoginModelImpl mLoginModel;
    private MApplication application;

    public LoginPresenter() {
        mLoginModel = new LoginModelImpl();
        if (application == null) {
            application = MApplication.getInstance();
        }
    }

    public void checkFormat(String account, String password) {
        if (TextUtils.isEmpty(account)) {
            getView().onCheckFormatFail("请输入账号");
        } else if (TextUtils.isEmpty(password)) {
            getView().onCheckFormatFail("请输入密码");
        } else if (password.length() < 6 || password.length() > 18) {
            getView().onCheckFormatFail("密码格式不正确");
        } else {
            // getView().onCheckFormatSuccess();
            // 显示进度条

            login(Integer.valueOf(account), password);
        }
    }

    public void login(Integer account, String password) {
        mLoginModel.login(account, password, new LRContract.LRCallBack() {
            @Override
            public void onSuccess() {

                getView().onLRSuccess();
                //System.out.println("xx");
            }

            @Override
            public void onFail(String errorInfo) {
                getView().onLRFail(errorInfo);
            }
        });
    }

    public void isNetWorkAvailable() {
        if (NetWorkUtil.isNetWorkAvailable((Context) getView())) {
            getView().NetWorkAvailable();
        } else {
            getView().NetWorkUnAvailable();
        }
    }

    @Override
    public void getMessage(TranObject msg) {
        if (msg != null) {
            // System.out.println("Login:" + msg);
            switch (msg.getType()) {
                case LOGIN: // LoginActivity只处理登录的消息
                    mLoginModel.getMessage(msg,new LRContract.LRCallBack() {
                    @Override
                    public void onSuccess() {

                        getView().onLRSuccess();
                        //System.out.println("xx");
                    }

                    @Override
                    public void onFail(String errorInfo) {
                        getView().onLRFail(errorInfo);
                    }
                });
                    break;
                default:
                    break;
            }
        }
    }
}
