package edu.ncu.zww.app.wei_im.mvp.model.impl;

import java.util.List;

import edu.ncu.zww.app.wei_im.client.ClientControl;
import edu.ncu.zww.app.wei_im.mvp.contract.OperaFGContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Invitation;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ResultBean;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.db.InvitationDao;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType.FRIEND_REQUEST;

public class NewContactModelImpl implements OperaFGContract.NewContactModel {

    private InvitationDao invitationDao;

    public NewContactModelImpl() {
        invitationDao = new InvitationDao();
    }


    @Override
    public List<Invitation> queryInvitations() {
        return invitationDao.queryInvitations();
    }

    @Override
    public Observable<ResultBean> accessInvitation(final Invitation invitation) {

        return Observable.create(new ObservableOnSubscribe<ResultBean>() {
            @Override
            public void subscribe(ObservableEmitter<ResultBean> emitter) throws Exception {
                // 给客户发消息
                ClientControl.sendConOrGroupRequest(invitation,FRIEND_REQUEST);

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
                    ResultBean result;
                    if (resultTran.getType().equals(FRIEND_REQUEST)) {
                        result= new ResultBean(true,"添加好友成功");
                    } else {
                        result= new ResultBean(false,"添加好友失败");
                    }
                    emitter.onNext(result); // 发射数据
                }
                emitter.onComplete();
            }
        });

    }

    @Override
    public void updateInvitation(Invitation invitation) {
        invitationDao.updateInvitation(invitation);
    }

    public void closeRealm() {
        invitationDao.closeRealm();
    }

}
