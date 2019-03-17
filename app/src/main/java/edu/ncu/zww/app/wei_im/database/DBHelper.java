package edu.ncu.zww.app.wei_im.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.ncu.zww.app.wei_im.commons.Constants;
import edu.ncu.zww.app.wei_im.utils.LogUtil;

public class DBHelper extends SQLiteOpenHelper {

    //数据库名
    private static final String DBNAME = Constants.DBNAME;
    //版本号
    private static final int VERSION = 1;
    //表名
    private static final String TABLENAME = "users";

    public DBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    /**
     * 该方法只执行一次，首次执行创建表
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtil.d(this+"执行onCreate");
        String sql = "CREATE table IF NOT EXISTS " + TABLENAME +
                " (id TEXT PRIMARY KEY, name TEXT, img TEXT, sex TEXT, isOnline TEXT, groups TEXT);";
        db.execSQL(sql);
    }


    /**
     * 版本更新调用该方法
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 还原旧版本
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
