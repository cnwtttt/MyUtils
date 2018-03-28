package com.wengtao.my_utils;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wt on 2018-2-11.
 */

public class FileUtil {
    public static final String MD5 = "MD5";

    private static FileUtil fileUtil = null;

    public synchronized static FileUtil getInstance() {
        if (fileUtil == null) {
            fileUtil = new FileUtil();
        }
        return fileUtil;
    }

    /**
     * 计算文件MD5值
     * 计算文件及文件夹的MD5
     *
     * @return
     */
    public String getFileMD5(File file) throws FileNotFoundException {
        String md5 = null;
        if (!file.exists()) {
            throw new FileNotFoundException("this file is no found");
        }
        MessageDigest messageDigest = null;
        FileInputStream fileInputStream = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            messageDigest = MessageDigest.getInstance(MD5);
            fileInputStream = new FileInputStream(file);
            while ((len = fileInputStream.read(buffer, 0, 1024)) != -1) {
                messageDigest.update(buffer, 0, len);
            }
            fileInputStream.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        md5 = new BigInteger(1, messageDigest.digest()).toString(16);
        return md5;
    }


    /**
     * 创建文件夹
     * 增加文件（非目录）存在判断，存在则删除创建文件夹
     *
     * @return
     */
    public boolean mkDir(String path) {
        boolean isMake = false;
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (!file.exists()) {
                isMake = file.mkdir();
            } else if (file.isDirectory()) {
                isMake = true;
            } else {
                file.delete();
                isMake = file.mkdir();
            }
        }
        return isMake;
    }


    /**
     * 获取所有文件
     *
     * @return
     */
    public List<File> getAllFiles(File root) throws FileNotFoundException {
        List<File> fileList = new ArrayList<>();
        if (root.exists()) {
            File[] files = root.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        fileList.addAll(getAllFiles(file));
                    } else {
                        fileList.add(file);
                    }
                }
            }
        } else {
            throw new FileNotFoundException("this file is no found");
        }
        return fileList;
    }


    /**
     * 删除文件
     *
     * @return
     */
    public boolean clearFiles(String path) {
        File file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            return file.delete();
        }
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File deleteFile :
                        files) {
                    clearFiles(deleteFile.getPath());
                }
            }
            file.delete();
            return true;
        }
        return false;
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    public String readFile(File file) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            stringBuffer.append(str).append("\n");
        }
        inputStream.close();
        return stringBuffer.toString();
    }

    public void writeFile(String content, File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        byte[] bytes;
        bytes = content.getBytes();
        int length = content.length();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bytes, 0, length);
    }
}
