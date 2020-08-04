package com.beta.demo.constant;

import java.io.File;

public interface UserConstant {

    /**
     * constant of
     * class : User
     * field : onlineState
     * 1 means user is online
     * 0 means user is offline
     */
    public static final Integer USER_STATE_ONLINE = 1;
    public static final Integer USER_STATE_OFFLINE = 0;


    public  static final String PORTRAIT_DOWNLOAD_PATH = File.separator + "upload" + File.separator + "portrait";
    /**
     * constant of
     * portrait file upload path
     */
    public static final String PORTRAIT_UPLOAD_PATH = File.separator + "WEB-INF" + UserConstant.PORTRAIT_DOWNLOAD_PATH;

}
