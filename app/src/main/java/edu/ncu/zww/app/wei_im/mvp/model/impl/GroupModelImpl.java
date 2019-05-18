package edu.ncu.zww.app.wei_im.mvp.model.impl;

import java.util.ArrayList;
import java.util.List;

import edu.ncu.zww.app.wei_im.client.ClientControl;
import edu.ncu.zww.app.wei_im.mvp.contract.ContactContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupInfo;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public class GroupModelImpl implements ContactContract.GroupModel {

    @Override
    public Observable<List<GroupInfo>> queryGroupList() {
        return Observable.create(new ObservableOnSubscribe<List<GroupInfo>>(){
            @Override
            public void subscribe(final ObservableEmitter<List<GroupInfo>> emitter) throws Exception {
                // 先从本地获取
                ApplicationData mData = ApplicationData.getInstance();
                List<GroupInfo> groupList = mData.getGroupList();

                // 如果本地没有数据
                if (groupList==null || groupList.size()==0) {
                    // 从服务器获取
                    ClientControl.getGroupList();

                    //这里要同步加锁，保证client获取数据操作mData时，这里需要等待
                    synchronized(mData){
                        if (!mData.isIsReceived()) { // 当数据没更新
                            try {
                                mData.wait(); // 释放该锁，自己进入阻塞等待
                            } catch(InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        groupList = mData.getGroupList();
                        // 取完数据将flag标志改为false，表明ApplicationData可以更新数据了
                        mData.setIsReceived(false);
                        // 唤醒输出线程，允许空唤醒，即没有需要被唤醒的也可以
                        mData.notify();

                    }
                } // end if
                emitter.onNext(groupList);
                emitter.onComplete();
            }
        });
    }
}
