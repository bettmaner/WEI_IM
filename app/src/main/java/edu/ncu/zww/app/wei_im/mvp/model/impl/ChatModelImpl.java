package edu.ncu.zww.app.wei_im.mvp.model.impl;

import edu.ncu.zww.app.wei_im.mvp.contract.ChatContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ResultBean;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import io.reactivex.Observable;

public class ChatModelImpl implements ChatContract.ChattModel {

    @Override
    public Observable<ResultBean> sendMsg(TranObject tranObj) {
        return null;
    }
}
