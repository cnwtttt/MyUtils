package com.wengtao.my_utils

import java.io.ByteArrayOutputStream
import java.io.InputStream

public class Command {
    val TAG = "Command"


    companion object {
        fun execCmd(vararg commands: String): String? {
            val process: Process
            val errIs: InputStream
            val inIs: InputStream
            var result: String? = null
            if (commands.isNotEmpty()) {
                try {
                    process = ProcessBuilder().command(*commands).start()
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    var read = 0
                    errIs = process.errorStream
                    do {
                        read = errIs.read()
                        byteArrayOutputStream.write(read)
                    } while (read != -1)

                    inIs = process.inputStream

                    do {
                        read = inIs.read()
                        byteArrayOutputStream.write(read)
                    } while (read != -1)

                    result = String(byteArrayOutputStream.toByteArray())
                    errIs.close()
                    inIs.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return result
        }
    }


}