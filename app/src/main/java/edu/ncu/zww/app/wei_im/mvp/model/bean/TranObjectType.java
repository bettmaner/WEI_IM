package edu.ncu.zww.app.wei_im.mvp.model.bean;

/**
 *  封装传输对象的业务类型，在TranObject使用.
 **/
public class TranObjectType {
    public final static String REGISTER = "REGISTER";   // 注册
    public final static String LOGIN = "LOGIN"; // 用户登录
    public final static String LOGOUT = "LOGOUT";   // 用户退出登录
    public final static String FRIENDLOGIN = "FRIENDLOGIN"; // 好友上线
    public final static String FRIENDLOGOUT = "FRIENDLOGOUT";   // 好友下线
    public final static String MESSAGE = "MESSAGE"; // 用户发送消息
    public final static String UNCONNECTED = "UNCONNECTED"; // 无法连接
    public final static String FILE = "FILE";// 传输文件
    public final static String REFRESH = "REFRESH"; // 刷新好友列表
}
