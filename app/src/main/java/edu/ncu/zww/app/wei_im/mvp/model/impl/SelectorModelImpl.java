package edu.ncu.zww.app.wei_im.mvp.model.impl;


import edu.ncu.zww.app.wei_im.client.ClientControl;
import edu.ncu.zww.app.wei_im.mvp.contract.ContactContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupInfo;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public class SelectorModelImpl implements ContactContract.SelectorModel {


    public Observable<GroupInfo> createGroup(final GroupInfo newGroup) {

        return Observable.create(new ObservableOnSubscribe<GroupInfo>() {
            @Override
            public void subscribe(ObservableEmitter<GroupInfo> emitter) throws Exception {
                // 提交至服务器
                ClientControl.createGroup(newGroup);

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
                    TranObject resultTran = mData.getReceivedMessage();
                    // 取完数据将flag标志改为false，表明ApplicationData可以更新数据了
                    mData.setIsReceived(false);
                    // 唤醒输出线程，允许空唤醒，即没有需要被唤醒的也可以
                    mData.notify();

                    GroupInfo groupInfo = (GroupInfo) resultTran.getObject();
                    emitter.onNext(groupInfo); // 发射数据
                }
                emitter.onComplete();
            }
        });
    }

}
