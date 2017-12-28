package com.wengtao.my_utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


/**
 * Created by wt on 2017-8-18.
 */

public class SharedPreferencesUtil {
    public static final String BASE_NAME = "SharedPreferencesUtil";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    private static SharedPreferencesUtil mSharedPreferencesUtil;

    private SharedPreferencesUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences(BASE_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public synchronized static SharedPreferencesUtil getInstance(Context context) {
        if (mSharedPreferencesUtil == null) {
            mSharedPreferencesUtil = new SharedPreferencesUtil(context);
        }
        return mSharedPreferencesUtil;
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public void setInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public void setLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    public void setFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public float getAFloat(String key) {
        return mSharedPreferences.getFloat(key, 0);
    }

    public void setString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, null);
    }

    public void setObject(String key, Object value) {
        editor.putString(key, CommonUtil.objectToString(value));
        editor.commit();
    }

    public Object getObject(String key) {
        Object object = null;
        String value = mSharedPreferences.getString(key, "");
        if (!TextUtils.isEmpty(value)) {
            object = CommonUtil.stringToObject(value);
        }
        return object;
    }

}
