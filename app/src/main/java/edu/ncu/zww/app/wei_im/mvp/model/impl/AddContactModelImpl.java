package edu.ncu.zww.app.wei_im.mvp.model.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import edu.ncu.zww.app.wei_im.client.ClientControl;
import edu.ncu.zww.app.wei_im.client.HttpUtil;
import edu.ncu.zww.app.wei_im.mvp.contract.OperaFGContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddContactModelImpl implements OperaFGContract.AddContactModel {

    public Observable<List> queryContacts(final String account, final Integer type){
        return Observable.create(new ObservableOnSubscribe<List>(){
            @Override
            public void subscribe(final ObservableEmitter<List> emitter) throws Exception {
                String url = HttpUtil.getQueryContactUrl(account,type);
                HttpUtil.sendOkHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.getStackTrace();
                        emitter.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        LogUtil.d("获取list线程:" + Thread.currentThread().getName());
                        System.out.println();
                        String responseText = response.body().string();
                        Gson gson = new Gson();
                        List<Contact> list = gson.fromJson(responseText,new TypeToken<List<Contact>>(){}.getType());
                        emitter.onNext(list);
                        emitter.onComplete();
                    }
                });
            }
        });
    }

    @Override
    public Observable<TranObject> addContact(final Integer account, final Integer type) {
        return Observable.create(new ObservableOnSubscribe<TranObject>(){

            @Override
            public void subscribe(ObservableEmitter<TranObject> emitter) throws Exception {
                ClientControl.sendContactRequest(account, type);
                // 接收服务器返回结果
                ApplicationData mData = ApplicationData.getInstance();
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

        });
    }

}
