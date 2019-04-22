package edu.ncu.zww.app.wei_im.utils;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.User;

/**
 * 对象转换工具
 * */

public class BeanTransfer {

    // 净化user，将带有秘密等敏感信息的user转为普通可供资料查看的user
    public static User filterUser(User myself) {
        User user = new User();
        user.setAccount(myself.getAccount());
        user.setName(myself.getName());
        user.setSex(myself.getSex());
        user.setImg(myself.getImg());
        return user;
    }

    public static List<Contact> UsersToFriends(List<User> users) {
        List<Contact> friends = new ArrayList<>();
        for (User user : users) {
            Contact friend = new Contact(user.getName(),1); // 1表示好友
            friend.setAccount(user.getAccount());
            friend.setImg(user.getImg());
            friend.setSex(user.getSex());
            friends.add(friend);
        }
        return friends;
    }
}
