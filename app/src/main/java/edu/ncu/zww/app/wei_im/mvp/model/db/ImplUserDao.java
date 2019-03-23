package edu.ncu.zww.app.wei_im.mvp.model.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import edu.ncu.zww.app.wei_im.database.DBHelper;
import edu.ncu.zww.app.wei_im.mvp.model.bean.User;

public class ImplUserDao implements UserDao {

    private DBHelper dbHelper;

    public ImplUserDao(Context context) {
        dbHelper = new DBHelper(context);
        System.out.println("开始实例化DBHelper");
    }

    @Override
    public void addUsers(List<User> users) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (User u : users) {
            db.execSQL("insert into users (account,name,img,sex,isOnline,groups) values(?,?,?,?,?,?)",
                    new Object[] { u.getAccount(), u.getName(), u.getImg(),
                            u.getSex(), u.getIsOnline(), u.getGroups() });
        }
        db.close();
    }

    @Override
    public User getUserById(Text account) {
        SQLiteDatabase db  = dbHelper.getReadableDatabase();
        User user = new User();
        Cursor c = db.rawQuery("select * from users where account = ?", new String[]{account+""});
        if (c.moveToFirst()) {
            user.setName(c.getString(c.getColumnIndex("name")));
            user.setImg(c.getInt(c.getColumnIndex("img")));
            user.setSex(c.getInt(c.getColumnIndex("sex")));
        }
        c.close();
        db.close();
        return user;
    }

    @Override
    public List<User> getFriends() {
        SQLiteDatabase db  = dbHelper.getReadableDatabase();
        List<User> friends = new ArrayList<User>();
        Cursor c = db.rawQuery("select * from users", null);
        while (c.moveToNext()) {
            User u = new User();
            u.setAccount(c.getInt(c.getColumnIndex("account")));
            u.setName(c.getString(c.getColumnIndex("name")));
            u.setImg(c.getInt(c.getColumnIndex("img")));
            u.setSex(c.getInt(c.getColumnIndex("sex")));
            u.setIsOnline(c.getInt(c.getColumnIndex("isOnline")));
            u.setGroups(c.getInt(c.getColumnIndex("groups")));
            friends.add(u);
        }
        c.close();
        db.close();
        return friends;
    }

    @Override
    public void updateUser(List<User> users) {
        if (users.size() > 0) {
            deleteAll();
            addUsers(users);
        }
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db  = dbHelper.getReadableDatabase();
        db.execSQL("delete from users");
        db.close();
    }
}
