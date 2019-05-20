package edu.ncu.zww.app.wei_im.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import edu.ncu.zww.app.wei_im.MApplication;
import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.mvp.contract.LRContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.impl.RegisterModelImpl;
import edu.ncu.zww.app.wei_im.utils.NetWorkUtil;

public class RegisterPresenter extends BasePresenter<LRContract.LRView> {

    private RegisterModelImpl mRegisterModel;
    private MApplication application;

    public RegisterPresenter() {
        mRegisterModel = new RegisterModelImpl();
        if (application == null) {
            application = MApplication.getInstance();
        }
    }

    public void checkFormat(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            getView().onCheckFormatFail("请输入邮箱");
        } else if (TextUtils.isEmpty(password)) {
            getView().onCheckFormatFail("请输入密码");
        } else if (password.length() < 6 || password.length() > 18) {
            getView().onCheckFormatFail("密码格式不正确");
        } else {
            // getView().onCheckFormatSuccess();
            // 显示进度条
            register(email, password);
        }
    }

    public void register(String email, String password) {
        mRegisterModel.register(email, password, new LRContract.LRCallBack() {
            @Override
            public void onSuccess(String info) {
                getView().onLRSuccess(info);
            }

            @Override
            public void onFail(String errorInfo) {
                getView().onLRFail(errorInfo);
            }
        });

    }

    public void isNetWorkAvailable() {
        if (NetWorkUtil.isNetWorkAvailable()) {
            getView().NetWorkAvailable();
        } else {
            getView().NetWorkUnAvailable();
        }
    }

    /*
    *  服务器响应的消息从读线程转交给服务，服务广播，Activity得到广播并调用此方法
    * */
    @Override
    public void getMessage(TranObject msg) {
//        if (msg.getType().equals(TranObjectType.REGISTER)) {
//            User user = (User) msg.getObject();
//            int account = user.getAccount();
//            System.out.println(account);
//            if (account > 0) {
//                getView().onLRSuccess(String.valueOf(account));
//            } else {
//                getView().onLRFail("注册失败，已有该账号");
//            }
//        }
    }

    @Override
    public void close() {

    }
}
