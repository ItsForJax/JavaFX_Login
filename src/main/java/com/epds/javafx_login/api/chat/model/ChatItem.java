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
    private String timestamp;

    @Override
    public String toString() {
        return "ChatItem{" +
                "is_support=" + is_support +
                ", message_id=" + message_id +
                ", message='" + message + '\'' +
                ", sender_email='" + sender_email + '\'' +
                ", timestamp='" + timestamp + '\'' +
                "}";
    }
}
