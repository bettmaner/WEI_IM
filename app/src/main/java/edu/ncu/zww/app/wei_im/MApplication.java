package edu.ncu.zww.app.wei_im;

import android.app.Application;

import edu.ncu.zww.app.wei_im.model.Model;

public class MApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("创建执行经济"+this);
        Model.getInstance().init(this);
    }
}
