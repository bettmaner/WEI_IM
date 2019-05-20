package edu.ncu.zww.app.wei_im.mvp.contract;

import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import io.reactivex.Observable;

public class ContactInfoContract  {

    /**
     *  view
     *  作用：view层得到要显示的数据
     */
    public interface ContactInfoView {
        void getContact(Integer account);
        void onError(String info);
        void onAddSuccess(String info);
        void onFail(String info);
    }

    /**
     *  model
     *  作用：model层根据参数向网络获取数据,并通过callBack回调给presenter
     */
    public interface ContactInfoModel {
        // 获取该Contact信息
        Observable<Contact> getContact(Integer account);
        // 删除该好友（已是好友状态）

        // 添加该联系人为好友

    }
}
