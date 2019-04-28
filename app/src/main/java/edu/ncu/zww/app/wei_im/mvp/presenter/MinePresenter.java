package edu.ncu.zww.app.wei_im.mvp.presenter;

import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.mvp.contract.MineContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.mvp.model.impl.MineModelImpl;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MinePresenter extends BasePresenter<MineContract.MineView> {

    private MineModelImpl mineModel;

    public MinePresenter() {
        this.mineModel = new MineModelImpl();
    }

    @Override
    public void getMessage(TranObject msg) {

    }

    // 退出登录
    public void logOff() {
        mineModel.logOff()
                .subscribeOn(Schedulers.newThread()) // 观察者的操作线程，Schedulers.newThread()启动新线程。
                .observeOn(AndroidSchedulers.mainThread())  // 在UI线程中执行结果（观察者操作）
                .subscribe(new Observer<String>(){
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String info) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }


    // 关闭应用
    public void clossApp() {

    }
}
