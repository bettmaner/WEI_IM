package edu.ncu.zww.app.wei_im.mvp.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.mvp.contract.ContactContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.impl.ContactModelImpl;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactPresenter extends BasePresenter<ContactContract.ContactView> {

    private ContactModelImpl mContactModel;

    public ContactPresenter() {
        this.mContactModel = new ContactModelImpl();
    }

    @Override
    public void getMessage(TranObject msg) {

    }

    public void queryFriends() {
        mContactModel.queryFriends()
                .subscribeOn(Schedulers.newThread()) // 观察者的操作线程，Schedulers.newThread()启动新线程。
                .observeOn(AndroidSchedulers.mainThread())  // 在UI线程中执行结果（观察者操作）
                .subscribe(new Observer<List<Contact>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Contact> friends) {

                        Collections.sort(friends, new Comparator<Contact>() {
                            @Override
                            public int compare(Contact c1, Contact c2) {
                                return c1.getPinyin().compareTo(c2.getPinyin());
                            }
                        });
                        List<Contact> notLetter = new ArrayList<>();
                        List<Contact> copyContacts = new ArrayList<>(friends);
                        for (Contact contact : copyContacts) {
                            if (contact.getLetter().equals("#")) {
                                notLetter.add(contact);
                                friends.remove(contact);
                            }
                        }
                        friends.addAll(notLetter);
                        copyContacts.clear();
                        getView().onQuerySuccess(friends);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
