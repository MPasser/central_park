package com.beta.demo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class StringUtils {
    public static String renameFilename(String filename){
        int dotIndex = filename.lastIndexOf(".");
        String suffix = filename.substring(dotIndex);
        return UUID.randomUUID().toString()+suffix;
    }
}
