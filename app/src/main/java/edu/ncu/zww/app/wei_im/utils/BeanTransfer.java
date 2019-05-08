package edu.ncu.zww.app.wei_im.utils;


import java.util.ArrayList;
import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupInfo;
import edu.ncu.zww.app.wei_im.mvp.model.bean.User;

/**
 * 对象转换工具
 * */

public class BeanTransfer {

    // 净化user，将带有秘密等敏感信息的user转为Contact
    public static Contact userToContact(User user) {
        Contact contact = new Contact();
        contact.setAccount(user.getAccount());
        contact.setName(user.getName());
        contact.setAvatar(user.getAvatar());
        contact.setSex(user.getSex());
        return contact;
    }

    public static List<Contact> UsersToFriends(List<User> users) {
        List<Contact> friends = new ArrayList<>();
        for (User user : users) {
            Contact friend = new Contact(user.getName(),1); // 1表示好友
            friend.setAccount(user.getAccount());
            friend.setAvatar(user.getAvatar());
            friend.setSex(user.getSex());
            friends.add(friend);
        }
        return friends;
    }

    public static GroupInfo ContactTOGroup(Contact contact) {
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGid(contact.getAccount());
        groupInfo.setName(contact.getName());
        groupInfo.setAvatar(contact.getAvatar());
        return groupInfo;
    }
}
