package com.beta.demo.constant;

import java.io.File;

public interface ChatMessageConstant {

    /**
     * constant used in pojo.ChatMessage
     * 0 means text message
     * 1 meas picture message
     * 2 means file message
     */
    public static final Integer CHAT_MESSAGE_TYPE_TEXT = 0;
    public static final Integer CHAT_MESSAGE_TYPE_FILE = 1;

    /**
     * constant of ChatroomWebsocket
     * to define the max length of messages
     */
    public static final Integer CHAT_MESSAGE_MAX_LENGTH = 120;


    /**
     * constant of
     * message file upload path and download path
     */
    public static final String MSG_FILE_DOWNLOAD_PATH = File.separator + "upload" + File.separator + "msgFile";
    public static final String MSG_FILE_UPLOAD_PATH = File.separator + "WEB-INF" + MSG_FILE_DOWNLOAD_PATH;


    /**
     * constant of
     * method to find message log
     * ALL means all messages
     * FILE means only file messages
     */
    public static final String MSG_LOG_SCOPE_ALL = "ALL";
    public static final String MSG_LOG_SCOPE_FILE = "FILE";

    /**
     * constant of
     * chat message file maximum size
     */
    public static final int CHAT_MSG_FILE_MAX_SIZE = 1024*3072;

}
