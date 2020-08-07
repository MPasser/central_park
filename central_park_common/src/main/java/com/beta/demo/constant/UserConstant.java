package com.beta.demo.constant;

import java.io.File;

public interface UserConstant {


    /**
     * constant of
     * portrait file upload path and download path
     */
    public static final String PORTRAIT_DOWNLOAD_PATH = File.separator + "upload" + File.separator + "portrait";
    public static final String PORTRAIT_UPLOAD_PATH = File.separator + "WEB-INF" + UserConstant.PORTRAIT_DOWNLOAD_PATH;

    /**
     * constant of
     * portrait maximum size
     */
    public static final int PORTRAIT_MAX_SIZE = 1024*3072;
}
