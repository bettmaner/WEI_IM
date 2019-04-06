package edu.ncu.zww.app.wei_im.mvp.model.impl;

import edu.ncu.zww.app.wei_im.MApplication;
import edu.ncu.zww.app.wei_im.client.Client;
import edu.ncu.zww.app.wei_im.client.ClientOutputThread;
import edu.ncu.zww.app.wei_im.mvp.contract.LRContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType;
import edu.ncu.zww.app.wei_im.mvp.model.bean.User;
import edu.ncu.zww.app.wei_im.utils.Encode;
import edu.ncu.zww.app.wei_im.utils.LogUtil;

public class RegisterModelImpl implements LRContract.RegisterModel {

    private MApplication application;

    public RegisterModelImpl() {
        if (application == null) {
            application = MApplication.getInstance();
        }
    }

    @Override
    public void register(String email, String password, LRContract.LRCallBack callBack) {
        LogUtil.d("LoginModelImpl中aplication的值："+application);
        System.out.println(application.isConnected());
        if (application.isConnected()) {
            LogUtil.d("LoginModelImpl已经连接服务");
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject<User> o = new TranObject<User>(TranObjectType.REGISTER);
            User u = new User();
            u.setEmail(email);
            u.setPassword(Encode.getEncode("MD5", password));
            o.setObject(u);
            out.setMsg(o);  // 通过客户端socket连接服务器输出
        } else {
            callBack.onFail("服务器暂未开放");
        }
    }
}
