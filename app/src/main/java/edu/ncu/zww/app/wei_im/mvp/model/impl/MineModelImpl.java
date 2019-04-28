package edu.ncu.zww.app.wei_im.mvp.model.impl;

import edu.ncu.zww.app.wei_im.client.ClientControl;
import edu.ncu.zww.app.wei_im.mvp.contract.MineContract;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class MineModelImpl implements MineContract.MineModel {

    @Override
    public Observable<String> logOff() {
        return Observable.create(new ObservableOnSubscribe<String>(){
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                ClientControl.LogOff();
            }
        });
    }

    @Override
    public Observable<String> closeApp() {
        return null;
    }
}
