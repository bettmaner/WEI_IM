package edu.ncu.zww.app.wei_im.commons;

public class Constants {
    //public static final String SERVER_IP = "192.168.1.100";// 服务器ip
    public static final String SERVER_IP = "192.168.37.2";// 服务器ip
    public static final int SERVER_PORT = 12345;// 服务器端口

    public static final int REGISTER_FAIL = 0;//注册失败
    public static final String ACTION = "edu.ncu.zww.app.wei_im.message";//消息广播action
    public static final String MSGKEY = "message";//消息的key
    public static final String IP_PORT = "ipPort";//保存ip、port的xml文件名
    public static final String SAVE_USER = "saveUser";//保存用户信息的xml文件名
    public static final String BACKKEY_ACTION="edu.ncu.zww.app.wei_im.backKey";//返回键发送广播的action
    public static final int NOTIFY_ID = 0x911;//通知ID
    public static final String DBNAME = "qq.db";//数据库名称

    /* 序列化对象包名修改 */
    // 即客户端和服务端包名
    public static final String BEAN_OLD_PACKAGE = "edu.ncu.zww.imserver.bean";
    public static final String BEAN_NEW_PACKAGE = "edu.ncu.zww.app.wei_im.mvp.model.bean";
}
