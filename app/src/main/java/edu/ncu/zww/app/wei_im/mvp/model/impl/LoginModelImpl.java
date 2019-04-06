package edu.ncu.zww.app.wei_im.mvp.model.impl;

import android.widget.Toast;

import org.litepal.LitePal;

import java.util.List;

import edu.ncu.zww.app.wei_im.MApplication;
import edu.ncu.zww.app.wei_im.client.Client;
import edu.ncu.zww.app.wei_im.client.ClientOutputThread;
import edu.ncu.zww.app.wei_im.commons.Constants;
import edu.ncu.zww.app.wei_im.mvp.contract.LRContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.User;
import edu.ncu.zww.app.wei_im.mvp.model.db.ImplUserDao;
import edu.ncu.zww.app.wei_im.mvp.model.db.UserDao;
import edu.ncu.zww.app.wei_im.utils.Encode;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import edu.ncu.zww.app.wei_im.utils.SharePreferenceUtil;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType;

/*
 *完成请求数据，并根据请求状态选择调用回调对应的接口
 * Presenter类会使用本类的函数并实现回调接口，通过回调接口来使用View模块的函数更新UI
  */
public class LoginModelImpl implements LRContract.LoginModel {

    private  MApplication application;

    public LoginModelImpl() {
        if (application == null) {
            application = MApplication.getInstance();
        }
    }

    public void login(Integer account, String password, final LRContract.LRCallBack callBack) {

        /*//在这里去获取网络数据，
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        // 成功则回调callBack的success，传入数据
        // 失败则回调callBack的errorinfo
        */
        LogUtil.d("LoginModelImpl中aplication的值："+application);
        System.out.println(application.isConnected());
        if (application.isConnected()) {
            LogUtil.d("LoginModelImpl已经连接服务");
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject<User> o = new TranObject<User>(TranObjectType.LOGIN);
            User u = new User();
            u.setAccount(account);
            u.setPassword(Encode.getEncode("MD5", password));
            o.setObject(u);
            out.setMsg(o);  // 通过客户端socket连接服务器输出
        } else {
            callBack.onFail("服务器暂未开放");
        }
    }

    public void getMessage(TranObject msg, final LRContract.LRCallBack callBack) {
        List<User> list = (List<User>) msg.getObject();
        if (list.size() > 0) {
            // 保存从服务器得到的本人信息
            SharePreferenceUtil util = new SharePreferenceUtil(application, Constants.SAVE_USER);
            util.setId(list.get(0).getAccount());
            util.setPassword(list.get(0).getPassword());
            util.setEmail(list.get(0).getEmail());
            util.setName(list.get(0).getName());
            util.setImg(list.get(0).getImg());
            util.setSex(list.get(0).getSex());
            LogUtil.d(this+"登录后保存个人信息");

            LitePal.saveAll(list);
//            LitePal.getDatabase();
            /*UserDao dao = new ImplUserDao(application);
            dao.addUsers(list);*/

            /*Intent i = new Intent(LoginActivity.this,
                    FriendListActivity.class);
            i.putExtra(Constants.MSGKEY, msg);
            startActivity(i);

            if (mDialog.isShowing())
                mDialog.dismiss();*/
//            finish();
            callBack.onSuccess("登陆成功");
            //Toast.makeText(application, "登录成功", Toast.LENGTH_SHORT).show();
        } else {
            callBack.onFail("亲！您的帐号或密码错误哦");
        }
    }
}
