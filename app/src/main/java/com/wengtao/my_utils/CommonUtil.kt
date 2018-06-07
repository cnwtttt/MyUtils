package com.wengtao.my_utils

import android.text.TextUtils
import android.util.Log

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Created by wt on 2017/12/28.
 */

object CommonUtil {
    private val TAG = "CommonUtil"


    /**
     * @param object:Object
     * @return java.lang.String
     * @title objectToString
     * @description 对象转为字符串
     * @author wttt
     * @time 2017/12/28  17:03
     * @version
     */
    fun objectToString(`object`: Any): String? {
        var bytesToHexString: String? = null
        try {
            val bos = ByteArrayOutputStream()
            val oos = ObjectOutputStream(bos)
            oos.writeObject(`object`)
            bytesToHexString = bytesToHexString(bos.toByteArray())
            bos.close()
            oos.close()
        } catch (e: IOException) {
            Log.d(TAG, "objectToString: IOException " + e.cause)
            e.printStackTrace()
        }

        return bytesToHexString
    }

    /**
     * @param hexStringToBytes:String
     * @return java.lang.Object
     * @title stringToObject
     * @description 字符串转为对象
     * @author wttt
     * @time 2017/12/28  17:02
     * @version
     */
    fun stringToObject(hexStringToBytes: String): Any? {
        var readObject: Any? = null
        if (TextUtils.isEmpty(hexStringToBytes)) {
            return readObject
        }
        try {
            val stringToBytes = StringToBytes(hexStringToBytes)
            val bis = ByteArrayInputStream(stringToBytes)
            val ois = ObjectInputStream(bis)
            readObject = ois.readObject()
            bis.close()
            ois.close()
        } catch (e: IOException) {
            Log.d(TAG, "stringToObject: IOException " + e.cause)
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            Log.d(TAG, "stringToObject: ClassNotFoundException " + e.cause)
            e.printStackTrace()
        }

        return readObject
    }


    /**
     * @param bArray:buffer[]
     * @return java.lang.String
     * @title bytesToHexString
     * @description buffer[]数组转化为字符串
     * @author wttt
     * @time 2017/12/28  17:01
     * @version
     */
    fun bytesToHexString(bArray: ByteArray?): String? {
        if (bArray == null) {
            return null
        }
        if (bArray.isEmpty()) {
            return ""
        }
        val sb = StringBuffer(bArray.size)
        var sTemp: String
        for (i in bArray.indices) {
            sTemp = Integer.toHexString(0xFF and bArray[i].toInt())
            if (sTemp.length < 2)
                sb.append(0)
            sb.append(sTemp.toUpperCase())
        }
        return sb.toString()
    }


    /**
     * @param data:String
     * @return buffer[]
     * @title StringToBytes
     * @description 字符串转为Bytes数组
     * @author wttt
     * @time 2017/12/28  17:01
     * @version 1.0
     */
    fun StringToBytes(data: String): ByteArray? {
        val hexString = data.toUpperCase().trim { it <= ' ' }
        if (hexString.length % 2 != 0) {
            return null
        }
        val retData = ByteArray(hexString.length / 2)
        var i = 0
        while (i < hexString.length) {
            val intCh: Int  // 两位16进制数转化后的10进制数
            val hexChar1 = hexString[i] ////两位16进制数中的第一位(高位*16)
            val intCh3: Int
            intCh3 = when (hexChar1) {
                in '0'..'9' -> (hexChar1.toInt() - 48) * 16   //// 0 的Ascll - 48
                in 'A'..'F' -> (hexChar1.toInt() - 55) * 16 //// A 的Ascll - 65
                else -> return null
            }
            i++
            val hexChar2 = hexString[i] ///两位16进制数中的第二位(低位)
            val intCh4: Int
            intCh4 = when (hexChar2) {
                in '0'..'9' -> hexChar2.toInt() - 48 //// 0 的Ascll - 48
                in 'A'..'F' -> hexChar2.toInt() - 55 //// A 的Ascll - 65
                else -> return null
            }
            intCh = intCh3 + intCh4
            retData[i / 2] = intCh.toByte()//将转化后的数放入Byte里
            i++
        }
        return retData
    }


    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     * @throws Exception
     */
    fun getMD5Str(str: String): String? {
        var md5: String? = null
        try {
            // 生成一个MD5加密计算摘要
            val md = MessageDigest.getInstance("MD5")
            // 计算md5函数
            md.update(str.toByteArray())
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            md5 = BigInteger(1, md.digest()).toString(16)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return md5
    }

}
