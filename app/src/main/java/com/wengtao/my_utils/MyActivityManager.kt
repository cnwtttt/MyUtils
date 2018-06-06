package com.wengtao.my_utils

import android.app.Activity
import java.util.*

class MyActivityManager private constructor() {
    val TAG = "MyActivityManager"

    private var activityStack: Stack<Activity> = Stack()

    companion object {
        private var instance: MyActivityManager? = null
            get() {
                if (field == null) {
                    field = MyActivityManager()
                }
                return field
            }

        @Synchronized
        fun get(): MyActivityManager {
            return instance!!
        }
    }


    /**
     * 将activity移出栈
     *
     * @param activity
     */
    fun popActivity(activity: Activity) {
        if (!activityStack.isEmpty()) {
            activityStack.remove(activity)
        }
    }

    /**
     * 结束指定activity
     *
     * @param activity
     */
    fun endActivity(activity: Activity) {
        if (activityStack.isNotEmpty()) {
            activity.finish()
            activityStack.remove(activity)
        }
    }


    /**
     * 获得当前的activity(即最上层)
     *
     * @return
     */
    fun currentActivity(): Activity? {
        var currentActivity: Activity? = null
        if (activityStack.isNotEmpty()) {
            currentActivity = activityStack.lastElement()
        }
        return currentActivity
    }


    /**
     * 将activity推入栈内
     *
     * @param activity
     */
    fun pushActivity(activity: Activity) {
        activityStack.add(activity)
    }

    /**
     * 弹出除cls外的所有activity
     *
     * @param cls
     */
    fun popAllActivityExceptOne(cls: Class<out Activity>) {
        while (true) {
            val activity = currentActivity()
            if (activity == null) {
                break
            }
            if (activity.javaClass == (cls)) {
                break
            }
            popActivity(activity)
        }
    }


    /**
     * 结束除cls之外的所有activity,执行结果都会清空Stack
     *
     * @param cls
     */
    fun finishAllActivityExceptionOne(cls: Class<out Activity>) {
        while (!activityStack.empty()) {
            var activity = currentActivity()
            if (activity == null) {
                break
            }
            if (activity.javaClass == cls) {
                popActivity(activity)
            } else {
                endActivity(activity)
            }
        }
    }
}