package edu.ncu.zww.app.wei_im;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

import edu.ncu.zww.app.wei_im.client.Client;
import edu.ncu.zww.app.wei_im.commons.Constants;
import edu.ncu.zww.app.wei_im.mvp.model.Model;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import edu.ncu.zww.app.wei_im.utils.SharePreferenceUtil;

public class MApplication extends Application {

    private static MApplication instance;

    private int newMsgNum = 0;// 后台运行的消息

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SharePreferenceUtil spUtil = SharePreferenceUtil.getInstance();
        spUtil.init(this); // 初始化SharePreferenceUtil
        LogUtil.d( "Ip:"+spUtil.getIp()+" port:"+spUtil.getPort());
        Client.getInstance().init(spUtil.getIp(), spUtil.getPort()); // 初始化Client
        Model.getInstance().init(this);
        LitePal.initialize(this);
        LogUtil.d("执行了application创建");
    }

    public static MApplication getInstance() {
        return instance;
    }
}
