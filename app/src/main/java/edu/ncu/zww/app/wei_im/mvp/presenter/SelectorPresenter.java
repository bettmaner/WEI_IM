package edu.ncu.zww.app.wei_im.mvp.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.mvp.contract.ContactContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupInfo;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ResultBean;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.impl.SelectorModelImpl;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SelectorPresenter extends BasePresenter<ContactContract.SelectorView> {

    private SelectorModelImpl mSelectorModel;

    public SelectorPresenter() {
        this.mSelectorModel = new SelectorModelImpl();
    }

    @Override
    public void getMessage(TranObject msg) {

    }

    // 提交按钮点击验证
    public void submit(Map<Integer, Contact> map) {
        if (map.size()<2) {
            getView().onError("至少选择两位联系人");
        } else {
            getView().onSubmitVailOk();
        }
    }

    // 提交事件
    public void createGroup(String groupName,Map<Integer, Contact> map) {
        List<Integer> memberList = new ArrayList<>();
        for (Map.Entry<Integer, Contact> entry : map.entrySet()) {
            // 添加成员账号
            memberList.add(entry.getKey());
            // 制作照片
        }
        Integer userAccount = ApplicationData.getInstance().getUserInfo().getAccount();
        memberList.add(userAccount); // 添加自己账号
        GroupInfo newGroup = new GroupInfo();
        //newGroup.setCreator();
        newGroup.setMemberList(memberList);
        newGroup.setCreator(userAccount);
        newGroup.setName(groupName);
        mSelectorModel.createGroup(newGroup)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GroupInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GroupInfo groupInfo) {
                        System.out.println(groupInfo);
                        getView().onCreatedSuccess(groupInfo);
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
