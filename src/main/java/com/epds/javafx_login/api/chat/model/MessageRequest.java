package com.epds.javafx_login.api.chat.model;

public class MessageRequest {
    private String timestamp;
    private String sender;
    private String msg;
    private boolean isSupport;

    public MessageRequest(String timestamp, String sender, String msg, boolean isSupport) {
        this.timestamp = timestamp;
        this.sender = sender;
        this.msg = msg;
        this.isSupport = isSupport;
    }

    // Getters and setters
    // ...
}
