package edu.ncu.zww.app.wei_im.model.db;

import edu.ncu.zww.app.wei_im.contract.LoginContract;

/*
 *完成请求数据，并根据请求状态选择调用回调对应的接口
 * Presenter类会使用本类的函数并实现回调接口，通过回调接口来使用View模块的函数更新UI
  */
public class LoginModelImpl implements LoginContract.LoginModel {
    @Override
    public void login(String name, String password, final LoginContract.LoginCallBack callBack) {
        //在这里去获取网络数据，
        // 成功则回调callBack的success，传入数据
        // 失败则回调callBack的errorinfo
    }
}
