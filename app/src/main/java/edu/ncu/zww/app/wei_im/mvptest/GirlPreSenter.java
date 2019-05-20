package edu.ncu.zww.app.wei_im.mvptest;

import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;

public class GirlPreSenter extends BasePresenter<IGirlView> {

    private IGirlModel mGirlModel = new GirlModelImpl();

    public void fetch() {

    }

    @Override
    public void getMessage(TranObject msg) {
        System.out.println("不用理会");
    }

    @Override
    public void close() {

    }
}
