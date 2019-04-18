package edu.ncu.zww.app.wei_im.mvp.model.impl;


import org.litepal.LitePal;
import org.reactivestreams.Subscriber;

import java.io.IOException;
import java.util.List;

import edu.ncu.zww.app.wei_im.MApplication;
import edu.ncu.zww.app.wei_im.client.Client;
import edu.ncu.zww.app.wei_im.client.ClientControl;
import edu.ncu.zww.app.wei_im.mvp.contract.LRContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.User;
import edu.ncu.zww.app.wei_im.utils.Encode;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import edu.ncu.zww.app.wei_im.utils.SharePreferenceUtil;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


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

    public void login(final Integer account, final String password, final LRContract.LRCallBack callBack) {

        Observable.create(new ObservableOnSubscribe<TranObject>(){ // 创建（create)一个被观察者(OnSubscribe)
            @Override
            public void subscribe(ObservableEmitter<TranObject> emitter) throws Exception {
                LogUtil.d("LoginModelImpl","login运行线程:" + Thread.currentThread().getName());
                Client client = Client.getInstance();
                if (client.isConnected()) {
                    LogUtil.d("LoginModelImpl已经连接服务");
                    User u = new User();
                    u.setAccount(account);
                    u.setPassword(Encode.getEncode("MD5", password));

                    // 客户端发送数据
                    ClientControl.login(u);

                    // 接收服务器返回结果
                    ApplicationData mData = ApplicationData.getInstance();
                    mData.initData();
                    //这里要同步加锁，保证client获取数据操作mData时，这里需要等待
                    synchronized(mData){
                        if (!mData.isIsReceived()) { // 当数据没更新
                            try {
                                mData.wait(); // 释放该锁，自己进入阻塞等待
                            } catch(InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        TranObject result = mData.getReceivedMessage();
                        // 取完数据将flag标志改为false，表明ApplicationData可以更新数据了
                        mData.setIsReceived(false);
                        // 唤醒输出线程，允许空唤醒，即没有需要被唤醒的也可以
                        mData.notify();

                        emitter.onNext(result); // 发射数据
                    }
                    emitter.onComplete();
                }
            }
        }).subscribeOn(Schedulers.newThread()) // 观察者的操作线程，Schedulers.newThread()启动新线程。
            .observeOn(AndroidSchedulers.mainThread())  // 在UI线程中执行结果（观察者操作）
            .subscribe(new Observer<TranObject>(){ // 订阅（subscribe）观察者（Subscriber）

                //出错的时候调用该方法
                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    LogUtil.d("network", "IO异常");
                }

                //接受发射的 onNext方法
                @Override
                public void onNext(TranObject result) {
                    LogUtil.d("LoginModelImpl","消费者线程:" + Thread.currentThread().getName());
                    if (result.getStatus() == 0) {
                        callBack.onSuccess("登陆成功");
                    } else {
                        callBack.onFail("亲！您的帐号或密码错误哦");
                    }
                }

                //事件接受完成后 调用该方法
                @Override
                public void onComplete() {

                }

                @Override
                public void onSubscribe(Disposable d) {

                }
            });

        //在这里去获取网络数据，
//        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("线程id"+android.os.Process.myPid());
//                LogUtil.d("LoginModelImpl中aplication的值："+application);
//                Client client = Client.getInstance();
//                if (client.isConnected()) {
//                    LogUtil.d("LoginModelImpl已经连接服务");
//                    User u = new User();
//                    u.setAccount(account);
//                    u.setPassword(Encode.getEncode("MD5", password));
//                    try {
//                        ClientControl.login(u);
//                        // 返回结果
//                        System.out.println("线程id"+android.os.Process.myPid());
//                        ApplicationData data = ApplicationData.getInstance();
//                        data.initData();
//                        data.start();
//                        if (data.isLoginResult()) {
//                            callBack.onSuccess("登陆成功");
//                        } else {
//                            callBack.onFail("亲！您的帐号或密码错误哦");
//                        }
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        LogUtil.d("network", "IO异常");
//                    }
//                } else {
//                    callBack.onFail("服务器暂未开放");
//                }
//            }
//        });
        // 成功则回调callBack的success，传入数据
        // 失败则回调callBack的errorinfo
    }

    public void getMessage(TranObject msg, final LRContract.LRCallBack callBack) {
        List<User> list = (List<User>) msg.getObject();
        if (list.size() > 0) {
            // 保存从服务器得到的本人信息
            SharePreferenceUtil util = SharePreferenceUtil.getInstance();
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
