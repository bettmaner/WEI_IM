package edu.ncu.zww.app.wei_im.mvp.presenter;

import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.mvp.contract.MsgContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.impl.MsgModelImpl;

public class MsgPresenter extends BasePresenter<MsgContract.MsgView> {

    private MsgModelImpl mMsgModel;

    public MsgPresenter() {
        this.mMsgModel = new MsgModelImpl();
    }

    @Override
    public void getMessage(TranObject msg) {

    }

}
