package edu.ncu.zww.app.wei_im.mvp.model.impl;

import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.contract.ContactContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class ContactModelImpl implements ContactContract.ContactModel {

    @Override
    public Observable<List<Contact>> queryFriends() {

        return Observable.create(new ObservableOnSubscribe<List<Contact>>(){
            @Override
            public void subscribe(final ObservableEmitter<List<Contact>> emitter) throws Exception {
                System.out.println("在线程"+Thread.currentThread().getName()+"取数据库好友");
                List<Contact> contactList = ApplicationData.getInstance().getFriendList();
                emitter.onNext(contactList);
            }
        });

    }
}
