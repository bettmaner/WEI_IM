package edu.ncu.zww.app.wei_im.mvp.model.impl;

import edu.ncu.zww.app.wei_im.MApplication;
import edu.ncu.zww.app.wei_im.client.Client;
import edu.ncu.zww.app.wei_im.client.ClientControl;
import edu.ncu.zww.app.wei_im.mvp.contract.LRContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.bean.User;
import edu.ncu.zww.app.wei_im.utils.Encode;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterModelImpl implements LRContract.RegisterModel {

    private MApplication application;

    public RegisterModelImpl() {
        if (application == null) {
            application = MApplication.getInstance();
        }
    }

    @Override
    public void register(final String email, final String password, final LRContract.LRCallBack callBack) {

        Observable.create(new ObservableOnSubscribe<TranObject>(){ // 创建（create)一个被观察者(OnSubscribe)
            @Override
            public void subscribe(ObservableEmitter<TranObject> emitter) throws Exception {
                Client client = Client.getInstance();
                if (client.isConnected()) {
                    User u = new User();
                    u.setEmail(email);
                    u.setPassword(Encode.getEncode("MD5", password));

                    // 客户端发送数据
                    ClientControl.register(u);

                    // 初始化ApplicationData，准备接收mData更新的数据
                    ApplicationData appData = ApplicationData.getInstance();
                    appData.initData();
                    //这里要同步加锁，保证client获取数据操作mData时，这里需要等待
                    synchronized(appData){
                        if (!appData.isIsReceived()) { // 当数据没更新
                            try {
                                appData.wait(); // 释放该锁，自己进入阻塞等待
                            } catch(InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        TranObject result = appData.getReceivedMessage();
                        System.out.println("注册返回的数据"+result);
                        // 取完数据将flag标志改为false，表明ApplicationData可以更新数据了
                        appData.setIsReceived(false);
                        // 唤醒输出线程，允许空唤醒，即没有需要被唤醒的也可以
                        appData.notify();

                        emitter.onNext(result); // 发射数据
                        emitter.onComplete();
                    }
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
                        if (result.getStatus() == 0) { // 成功
                            callBack.onSuccess(result.getInfo());
                        } else {
                            callBack.onFail(result.getInfo());
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
//        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                LogUtil.d("LoginModelImpl中aplication的值："+application);
//                Client client = Client.getInstance();
//                if (client.isConnected()) {
//                    LogUtil.d("LoginModelImpl已经连接服务");
//                    User u = new User();
//                    u.setEmail(email);
//                    u.setPassword(Encode.getEncode("MD5", password));
//                    try {
//                        ClientControl.register(u);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    callBack.onFail("服务器暂未开放");
//                }
//            }
//        });
    }
}
