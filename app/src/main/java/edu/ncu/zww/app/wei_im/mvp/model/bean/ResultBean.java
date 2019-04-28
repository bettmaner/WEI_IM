package edu.ncu.zww.app.wei_im.mvp.model.bean;

// 用作model传给presenter的结果封装
public class ResultBean {

    private boolean success;

    private String info;

    public ResultBean(boolean success, String info) {
        this.success = success;
        this.info = info;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
