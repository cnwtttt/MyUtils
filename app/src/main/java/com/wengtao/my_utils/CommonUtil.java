package com.wengtao.my_utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by wt on 2017/12/28.
 */

public class CommonUtil {
    private static final String TAG = "CommonUtil";


    /**
     * @title objectToString
     * @description 对象转为字符串
     * @param object:Object
     * @return java.lang.String
     * @author wttt
     * @time 2017/12/28  17:03
     * @version
     */
    public static String objectToString(Object object) {
        String bytesToHexString = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            bytesToHexString = bytesToHexString(bos.toByteArray());
            bos.close();
            oos.close();
        } catch (IOException e) {
            Log.d(TAG, "objectToString: IOException " + e.getCause());
            e.printStackTrace();
        }
        return bytesToHexString;
    }

    /**
     * @title stringToObject
     * @description 字符串转为对象
     * @param hexStringToBytes:String
     * @return java.lang.Object
     * @author wttt
     * @time 2017/12/28  17:02
     * @version
     */
    public static Object stringToObject(String hexStringToBytes) {
        Object readObject = null;
        if (TextUtils.isEmpty(hexStringToBytes)) {
            return readObject;
        }
        try {
            byte[] stringToBytes = StringToBytes(hexStringToBytes);
            ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            readObject = ois.readObject();
            bis.close();
            ois.close();
        } catch (IOException e) {
            Log.d(TAG, "stringToObject: IOException " + e.getCause());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "stringToObject: ClassNotFoundException " + e.getCause());
            e.printStackTrace();
        }
        return readObject;
    }


    /**
     * @title bytesToHexString
     * @description  byte[]数组转化为字符串
     * @param  bArray:byte[]
     * @return java.lang.String
     * @author wttt
     * @time 2017/12/28  17:01
     * @version
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }


    /**
     * @title StringToBytes
     * @description 字符串转为Bytes数组
     * @param  data:String
     * @return byte[]
     * @author wttt
     * @time 2017/12/28  17:01
     * @version 1.0
     */
    public static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch3;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch3 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch3 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch4;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch4 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch4 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch3 + int_ch4;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }

}
