package edu.ncu.zww.app.wei_im;

import android.app.Application;

import edu.ncu.zww.app.wei_im.client.Client;
import edu.ncu.zww.app.wei_im.commons.Constants;
import edu.ncu.zww.app.wei_im.mvp.model.Model;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import edu.ncu.zww.app.wei_im.utils.SharePreferenceUtil;

public class MApplication extends Application {

    private Client client; // 客户端
    private boolean isConnected; // 客户端是否连接

    private int newMsgNum = 0;// 后台运行的消息

    @Override
    public void onCreate() {
        super.onCreate();
        SharePreferenceUtil spUtil = new SharePreferenceUtil(this, Constants.SAVE_USER);
        LogUtil.d(spUtil + "Ip:"+spUtil.getIp()+" port:"+spUtil.getPort());
        client = new Client(spUtil.getIp() , spUtil.getPort()); // 从保存文件中获取ip和端口
        Model.getInstance().init(this);
        LogUtil.d("执行了application创建");
    }

    public Client getClient() {
        return client;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }
}
