package com.wengtao.my_utils

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils

class SharedPreferencesUtil private constructor(mContext: Context) {
    val BASE_TIME = "SharedPreferencesUtil"
    private var mSharedPreferences: SharedPreferences
    private var mEditor: SharedPreferences.Editor

    init {
        mSharedPreferences = mContext.getSharedPreferences(BASE_TIME, Context.MODE_PRIVATE)
        mEditor = mSharedPreferences.edit()
    }

    companion object {
        private var instance: SharedPreferencesUtil? = null

        @Synchronized
        fun getInstance(context: Context): SharedPreferencesUtil {
            if (instance == null) {
                instance = SharedPreferencesUtil(context)
            }
            return instance!!
        }
    }

    fun setBoolean(key: String, value: Boolean) {
        mEditor.putBoolean(key, value)
        mEditor.commit()
    }

    fun getBoolean(key: String): Boolean {
        return mSharedPreferences.getBoolean(key, false)
    }

    fun setInt(key: String, value: Int) {
        mEditor.putInt(key, value)
        mEditor.commit()
    }

    fun getInt(key: String): Int {
        return mSharedPreferences.getInt(key, 0)
    }

    fun setLong(key: String, value: Long) {
        mEditor.putLong(key, value)
        mEditor.commit()
    }

    fun getLong(key: String): Long {
        return mSharedPreferences.getLong(key, 0)
    }

    fun setFloat(key: String, value: Float) {
        mEditor.putFloat(key, value)
        mEditor.commit()
    }

    fun getFloat(key: String): Float {
        return mSharedPreferences.getFloat(key, 0F)
    }

    fun setString(key: String, value: String) {
        mEditor.putString(key, value)
        mEditor.commit()
    }

    fun getString(key: String): String {
        return mSharedPreferences.getString(key, null)
    }

    fun setObject(key: String, value: Any) {
        mEditor.putString(key, CommonUtil.objectToString(value))
        mEditor.commit()
    }

    fun getObject(key: String): Any? {
        var objects: Any? = null
        val value = mSharedPreferences.getString(key, "")
        if (!TextUtils.isEmpty(value)) {
            objects = CommonUtil.StringToBytes(value)
        }
        return objects
    }
}