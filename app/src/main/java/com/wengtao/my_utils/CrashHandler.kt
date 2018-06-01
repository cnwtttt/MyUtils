package com.wengtao.my_utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.util.Log
import android.widget.Toast

import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.lang.Thread.UncaughtExceptionHandler
import java.lang.reflect.Field
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 */
class CrashHandler private constructor() : UncaughtExceptionHandler {

    private var mDefaultHandler: UncaughtExceptionHandler? = null
    private var mContext: Context? = null
    private val infos = HashMap<String, String>()
    private val formatter = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault())

    fun init(context: Context) {
        mContext = context
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                Log.e(TAG, "error : ", e)
            }

            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) {
            return false
        }
        //使用Toast来显示异常信息
        object : Thread() {
            override fun run() {
                Looper.prepare()
                Toast.makeText(mContext, "哇,程序挂掉了,即将退出.", Toast.LENGTH_LONG).show()
                Looper.loop()
            }
        }.start()
        //收集设备参数信息
        collectDeviceInfo(mContext)
        //保存日志文件
        saveCrashInfo2File(ex)
        return true
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    fun collectDeviceInfo(ctx: Context?) {
        try {
            val pm = ctx!!.packageManager
            val pi = pm.getPackageInfo(ctx.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                infos["versionName"] = versionName
                infos["versionCode"] = versionCode
            }
        } catch (e: NameNotFoundException) {
            Log.e(TAG, "an error occured when collect package info", e)
        }

        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                infos[field.name] = field.get(null).toString()
                Log.d(TAG, field.name + " : " + field.get(null))
            } catch (e: Exception) {
                Log.e(TAG, "an error occured when collect crash info", e)
            }

        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private fun saveCrashInfo2File(ex: Throwable): String? {

        val sb = StringBuffer()
        for ((key, value) in infos) {
            sb.append("$key=$value\n")
        }

        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        try {
            val timestamp = System.currentTimeMillis()
            val time = formatter.format(Date())
            val fileName = "crash-$time-$timestamp.log"
            //			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            val path = Environment.getDataDirectory().toString() + "/wttt/crash/"
            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val fos = FileOutputStream(path + fileName)
            fos.write(sb.toString().toByteArray())
            fos.close()
            //			}

            return fileName
        } catch (e: Exception) {
            Log.e(TAG, "an error occured while writing file...", e)
        }

        return null
    }

    companion object {

        val TAG = "CrashHandler"
        val instance = CrashHandler()
    }
}
