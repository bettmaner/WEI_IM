package edu.ncu.zww.app.wei_im.utils;

import android.content.Context;
import android.content.SharedPreferences;

import edu.ncu.zww.app.wei_im.commons.Constants;

/**
 * SharedPreferences工具类，单例模式
 * 因为只操作同一个文件，所以用单例模式方便些
 * */
public class SharePreferenceUtil {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static SharePreferenceUtil spUtil;
    static{
        spUtil= new SharePreferenceUtil();
    }

    public static SharePreferenceUtil getInstance(){
        if(spUtil == null){
            spUtil = new SharePreferenceUtil();
        }
        return spUtil;
    }

    public void init(Context context) {
        sp = context.getSharedPreferences(Constants.SAVE_USER, context.MODE_PRIVATE);
        editor = sp.edit();
    }

    // 用户的密码
    public void setPassword(String password) {
        editor.putString("password", password);
        editor.commit();
    }

    public String getPassword() {
        return sp.getString("password", "");
    }

    // 用户的id，即QQ号
    public void setId(Integer id) {
        editor.putInt("id", id);
        editor.commit();
    }

    public Integer getId() {
        return sp.getInt("id", 0);
    }

    // 用户的昵称
    public String getName() {
        return sp.getString("name", "");
    }

    public void setName(String name) {
        editor.putString("name", name);
        editor.commit();
    }

    // 用户的邮箱
    public String getEmail() {
        return sp.getString("email", "");
    }

    public void setEmail(String email) {
        editor.putString("email", email);
        editor.commit();
    }

    // 用户自己的头像
    public String getImg() {
        return sp.getString("img", "");
    }

    public void setImg(String i) {
        editor.putString("img", i);
        editor.commit();
    }

    // 用户性别
    public Integer getSex() {
        return sp.getInt("sex", 0);
    }

    public void setSex(int i) {
        editor.putInt("sex", i);
        editor.commit();
    }

    // ip
    public void setIp(String ip) {
        editor.putString("ip", ip);
        editor.commit();
    }

    public String getIp() {
        return sp.getString("ip", Constants.SERVER_IP);
    }

    // 端口
    public void setPort(int port) {
        editor.putInt("port", port);
        editor.commit();
    }

    public int getPort() {
        return sp.getInt("port", Constants.SERVER_PORT);
    }

    // 是否在后台运行标记
    public void setIsStart(boolean isStart) {
        editor.putBoolean("isStart", isStart);
        editor.commit();
    }

    // 是否正在后台运行
    public boolean getIsStart() {
        return sp.getBoolean("isStart", false);
    }

    // 是否第一次运行本应用
    public void setIsFirst(boolean isFirst) {
        editor.putBoolean("isFirst", isFirst);
        editor.commit();
    }

    public boolean getisFirst() {
        return sp.getBoolean("isFirst", true);
    }
}
