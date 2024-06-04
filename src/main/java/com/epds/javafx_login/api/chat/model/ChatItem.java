package com.epds.javafx_login.api.chat.model;

import com.google.gson.annotations.SerializedName;

public class ChatItem {

    @SerializedName("isSupport")
    private boolean support;

    @SerializedName("msg_id")
    private int message_id;

    @SerializedName("msg")
    private String message;

    @SerializedName("sender")
    private String sender_email;

    @SerializedName("timestamp")
    private String timestamp;


    // Getters and Setters
    public boolean isSupport() {
        return support;
    }

    public void setSupport(boolean support) {
        this.support = support;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender_email() {
        return sender_email;
    }

    public void setSender_email(String sender_email) {
        this.sender_email = sender_email;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ChatItem{" +
                "is_support=" + support +
                ", message_id=" + message_id +
                ", message='" + message + '\'' +
                ", sender_email='" + sender_email + '\'' +
                ", timestamp='" + timestamp + '\'' +
                "}";
    }
}
