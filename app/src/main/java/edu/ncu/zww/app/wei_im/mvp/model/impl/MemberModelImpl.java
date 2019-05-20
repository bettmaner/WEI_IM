package edu.ncu.zww.app.wei_im.mvp.model.impl;

import java.util.List;

import edu.ncu.zww.app.wei_im.client.ClientControl;
import edu.ncu.zww.app.wei_im.mvp.contract.MemberContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupInfo;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupMember;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.db.GroupDao;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.RealmList;

public class MemberModelImpl implements MemberContract.MemberModel {

    private GroupDao groupDao;

    public MemberModelImpl() {
        groupDao = new GroupDao();
    }

    @Override
    public GroupInfo getGroupInfo(int gid) {
        return groupDao.getGroupInfo(gid);
    }

    public Observable<List<Contact>> queryMemberList(final int gid) {
        return Observable.create(new ObservableOnSubscribe<List<Contact>>(){
            @Override
            public void subscribe(final ObservableEmitter<List<Contact>> emitter) throws Exception {
                // 先从本地获取

                List<Contact> memberList =  groupDao.getMemberListSync(gid);

                // 如果本地没有数据
                if (memberList==null || memberList.size()==0) {
                    // 从服务器获取
                    ClientControl.getGroupMember(gid);
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
                        TranObject tranObject = mData.getReceivedMessage();
                        memberList = (List<Contact>) tranObject.getObject();
                        // 取完数据将flag标志改为false，表明ApplicationData可以更新数据了
                        mData.setIsReceived(false);
                        // 唤醒输出线程，允许空唤醒，即没有需要被唤醒的也可以
                        mData.notify();
                        emitter.onNext(memberList);

                        // 保存到数据库
                        GroupMember groupMember = new GroupMember();
                        groupMember.setGid(gid);
                        RealmList<Contact> realmList = new RealmList<>();
                        realmList.addAll(memberList);
                        groupMember.setMemberList(realmList);
                        groupDao.saveMemberListSync(groupMember);

                    }
                } else {
                    emitter.onNext(memberList);
                }
                emitter.onComplete();
            }
        });
    }

    public void closeRealm() {
        groupDao.closeRealm();
    }
}
