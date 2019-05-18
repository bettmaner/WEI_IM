package edu.ncu.zww.app.wei_im.mvp.presenter;

import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.mvp.contract.MemberContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.impl.MemberModelImpl;

public class MemberPresenter extends BasePresenter<MemberContract.MemberView> {

    private MemberModelImpl mMemberModel;

    public MemberPresenter() {
        this.mMemberModel = new MemberModelImpl();
    }

    @Override
    public void getMessage(TranObject msg) {

    }
}
