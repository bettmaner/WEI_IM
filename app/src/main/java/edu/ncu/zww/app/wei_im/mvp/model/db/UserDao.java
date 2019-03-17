package edu.ncu.zww.app.wei_im.mvp.model.db;

import org.w3c.dom.Text;

import java.util.List;

import edu.ncu.zww.app.wei_im.mvp.model.bean.User;

public interface UserDao {
    void addUsers(List<User> users);
    User getUserById(Text id);
    List<User>  getFriends();
    void updateUser(List<User> users);
    void deleteAll();
}
