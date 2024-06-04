package com.epds.javafx_login.api.chat.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ChatItem {

    @SerializedName("isSupport")
    private boolean is_support;

    @SerializedName("msg_id")
    private int message_id;

    @SerializedName("msg")
    private String message;

    @SerializedName("sender")
    private String sender_email;

    @SerializedName("timestamp")
    private Date timestamp;
}
