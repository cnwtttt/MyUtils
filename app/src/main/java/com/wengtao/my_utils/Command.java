package com.wengtao.my_utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by wt on 2017-1-6.
 */

public class Command {
    public static String execCmd(String... command) {
        Process process;
        InputStream errIs, inIs;
        String result;
        try {
            process = new ProcessBuilder().command(command).start();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read;
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }

            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }

            result = new String(baos.toByteArray());

            errIs.close();
            inIs.close();
        } catch (Exception e) {
            result = e.toString();
        }
        return result;
    }
}
