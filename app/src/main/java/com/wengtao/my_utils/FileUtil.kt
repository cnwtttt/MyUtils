package com.wengtao.my_utils

import java.io.*
import java.math.BigInteger
import java.security.MessageDigest


public class FileUtil private constructor() {
    val TAG = "FileUtil"

    val MD5 = "MD5"

    companion object {
        private var instance: FileUtil? = null
            get() {
                if (field == null) {
                    field = FileUtil()
                }
                return field
            }

        @Synchronized
        fun get(): FileUtil {
            return instance!!
        }
    }

    /**
     * 计算文件MD5值
     * 计算文件及文件夹的MD5
     *
     * @return
     */
    @Throws(FileNotFoundException::class)
    fun getFileMD5(file: File): String? {
        if (!file.exists()) {
            throw FileNotFoundException("this file is no found")
        }
        val md5: String?
        val messageDigest: MessageDigest = MessageDigest.getInstance(MD5)
        val fileInputStream = FileInputStream(file)
        val buffer = ByteArray(1024)
        var len: Int
        do {
            len = fileInputStream.read(buffer, 0, 1024)
            messageDigest.update(buffer, 0, len)
        } while (len != -1)
        fileInputStream.close()
        md5 = BigInteger(1, messageDigest.digest()).toString(16)
        return md5
    }

    /**
     * 创建文件夹
     * 增加文件（非目录）存在判断，存在则删除创建文件夹
     *
     * @return
     */
    fun makeDir(path: String): Boolean {
        var isMake = false
        if (!path.isEmpty()) {
            var file = File(path)
            if (!file.exists()) {
                isMake = file.mkdir()
            } else if (file.isDirectory) {
                isMake = true
            } else {
                file.delete()
                isMake = file.mkdir()
            }
        }
        return isMake
    }

    @Throws(FileNotFoundException::class)
    fun getAllFile(root: File): List<File> {
        val fileList = ArrayList<File>()
        if (root.exists()) {
            val files = root.listFiles()
            if (files != null && files.isNotEmpty()) {
                for (file in files) {
                    if (file.isDirectory) {
                        fileList.addAll(getAllFile(file))
                    } else {
                        fileList.add(file)
                    }
                }
            }
        } else {
            throw FileNotFoundException("this file is no found")
        }
        return fileList
    }

    /**
     * 删除文件
     *
     * @return
     */
    fun clearFiles(path: String): Boolean {
        var file = File(path)
        if (file.exists() && !file.isDirectory) {
            return file.delete()
        }
        if (file.exists() && file.isDirectory) {
            var files = file.listFiles()
            if (files != null && files.isNotEmpty()) {
                for (deleteFile in files) {
                    clearFiles(deleteFile.path)
                }
            }
            file.delete()
            return true
        }
        return false
    }


    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    fun copyFile(oldPath: String, newPath: String) {
        var byteSum = 0
        var byteRead = 0
        val oldFile = File(oldPath)
        if (oldFile.exists()) {
            val inputStream = FileInputStream(oldFile)
            val fileOutputStream = FileOutputStream(newPath)
            val buffer = ByteArray(1024)
            var lenth = 0
            do {
                byteSum += byteRead
                fileOutputStream.write(buffer, 0, byteRead)
                byteRead = inputStream.read(buffer)
            } while (byteRead != -1)
            inputStream.close()
        }
    }

    @Throws(IOException::class)
    fun readFile(file: File): String {
        val stringBuffer = StringBuffer()
        val inputStream = FileInputStream(file)
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var str: String? = null
        do {
            stringBuffer.append(str).append("\n")
            str = bufferedReader.readLine()
        } while (str != null)
        inputStream.close()
        return stringBuffer.toString()
    }

    @Throws(IOException::class)
    fun writeFile(content: String, file: File) {
        if (!file.exists()) {
            file.createNewFile()
        }
        val bytes: ByteArray = content.toByteArray()
        val length = content.length
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(bytes, 0, length)

    }


}