package edu.ncu.zww.app.wei_im.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;

public class ContactSortUtil {

    // 好友仿微信按拼音首字母排序
    public static List<Contact> sortFriends(List<Contact> friends) {
        Collections.sort(friends, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getPinyin().compareTo(c2.getPinyin());
            }
        });
        List<Contact> notLetter = new ArrayList<>();
        List<Contact> copyContacts = new ArrayList<>(friends);
        for (Contact contact : copyContacts) {
            if (contact.getLetter().equals("#")) {
                notLetter.add(contact);
                friends.remove(contact);
            }
        }
        friends.addAll(notLetter);
        copyContacts.clear();
        return friends;
    }
}
