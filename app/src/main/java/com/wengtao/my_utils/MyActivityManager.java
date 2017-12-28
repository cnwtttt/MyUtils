package com.wengtao.my_utils;

import android.app.Activity;
import android.app.ActivityManager;

import java.util.Stack;

/**
 * Created by wt on 2017-11-9.
 */

public class MyActivityManager {
    private static final String TAG = "MyActivityManager";

    private static MyActivityManager myActivityManager = null;
    private Stack<Activity> activityStack = null;

    private MyActivityManager() {

    }

    public static MyActivityManager getInstance() {
        if (myActivityManager == null) {
            myActivityManager = new MyActivityManager();
        }
        return myActivityManager;
    }


    /**
     * 将activity移出栈
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        if (activity != null && activityStack != null && !activityStack.empty()) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定activity
     *
     * @param activity
     */
    public void endActivity(Activity activity) {
        if (activity != null && activityStack != null && !activityStack.empty()) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 获得当前的activity(即最上层)
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (activityStack != null && !activityStack.empty())
            activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 将activity推入栈内
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 弹出除cls外的所有activity
     *
     * @param cls
     */
    public void popAllActivityExceptOne(Class<? extends Activity> cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 结束除cls之外的所有activity,执行结果都会清空Stack
     *
     * @param cls
     */
    public void finishAllActivityExceptOne(Class<? extends Activity> cls) {
        if (activityStack == null) return;
        while (!activityStack.empty()) {
            Activity activity = currentActivity();
            if (activity.getClass().equals(cls)) {
                popActivity(activity);
            } else {
                endActivity(activity);
            }
        }
    }

    /**
     * 结束所有activity
     */
    public void finishAllActivity() {
        if (activityStack == null) return;
        while (!activityStack.empty()) {
            Activity activity = currentActivity();
            endActivity(activity);
        }
    }
}
