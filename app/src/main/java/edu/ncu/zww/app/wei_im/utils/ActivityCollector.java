package edu.ncu.zww.app.wei_im.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;


/**
 * 活动管理类
 * */
public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    // 建立activity时在onCreate调用
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    // 销毁activity时在onCreate调用
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    // 结束所有activity
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    // 打印所有activity
    public void    printActivity() {
        System.out.println("----------------- 存在的activity -------------------");
        for (Activity activity : activities) {
            System.out.println(activity);
        }
        System.out.println("----------------------------------------------------");
    }
}
