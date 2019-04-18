package edu.ncu.zww.app.wei_im.utils;


import edu.ncu.zww.app.wei_im.mvp.model.bean.User;

public class UserFilter {

    // 净化user，将带有秘密等敏感信息的user转为普通可供资料查看的user
    public static User filterUser(User myself) {
        User user = new User();
        user.setAccount(myself.getAccount());
        user.setName(myself.getName());
        user.setSex(myself.getSex());
        user.setImg(myself.getImg());
        return user;

    }
}
