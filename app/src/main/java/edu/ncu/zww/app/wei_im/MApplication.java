package edu.ncu.zww.app.wei_im;

import android.app.Application;

import com.dou361.dialogui.DialogUIUtils;

import org.litepal.LitePal;

import edu.ncu.zww.app.wei_im.client.Client;
import edu.ncu.zww.app.wei_im.database.RealmHelper;
import edu.ncu.zww.app.wei_im.mvp.model.Model;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import edu.ncu.zww.app.wei_im.utils.NetWorkUtil;
import edu.ncu.zww.app.wei_im.utils.SharePreferenceUtil;
import io.realm.Realm;


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
        NetWorkUtil.initialize(this);
        Client.getInstance().init(spUtil.getIp(), spUtil.getPort()); // 初始化Client
        Model.getInstance().init(this);
        LitePal.initialize(this);
        Realm.init(this); // 初始化realm数据库，在登录后还可更改配置选择数据库
        DialogUIUtils.init(this); // 使用弹出框插件https://github.com/lingcimi/jjdxm_dialogui
        LogUtil.d("执行了application创建");

       // RealmHelper.getInstance().initDB("100066");

    }

    public static MApplication getInstance() {
        return instance;
    }
}
