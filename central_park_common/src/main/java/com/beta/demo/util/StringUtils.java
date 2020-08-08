package com.beta.demo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils {
    public static String getRandomFilename(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        String suffix = filename.substring(dotIndex);
        return UUID.randomUUID().toString() + suffix;
    }

    public static String renameFilename(String filename, String dirPath) throws FileNotFoundException {
        String uniqFilename = filename;

        // TODO 路径问题

        File fileDir = new File(dirPath);

        if (fileDir.isDirectory()) {
            boolean stillDuplicated;

            String[] names = fileDir.list();

            do {
                stillDuplicated = false;
                for (String s : names) {
                    if (s.equals(uniqFilename)) {
                        stillDuplicated = true;
                    }
                }
                if (stillDuplicated) {
                    uniqFilename = growName(uniqFilename);
                }
            } while (stillDuplicated);

            return uniqFilename;
        } else {
            throw new FileNotFoundException("无法找到服务器的文件存储文件夹");
        }
    }

    private static String growName(String filename) {
        String newName = null;

        int dotIndex = filename.lastIndexOf(".");
        String prefix;
        String suffix;

        if (-1 == dotIndex){
            prefix = filename;
            suffix = "";
        }else {
            prefix = filename.substring(0, dotIndex);  //135465315461gif(1)
            suffix = filename.substring(dotIndex);  // .JPG
        }



        Pattern pattern = Pattern.compile("(.*)\\((\\d)\\)$");
        Matcher matcher = pattern.matcher(prefix);

        if (matcher.matches()) {
            String strName = matcher.group(1);

            int i = Integer.parseInt(matcher.group(2));  // 1
            if (i > 0) {
                newName = strName + "(" + (i + 1) + ")" + suffix;
            }
        } else {
            newName = prefix + "(1)" + suffix;
        }
        return newName;
    }

}
