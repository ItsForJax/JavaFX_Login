package com.epds.javafx_login.models;

public class ChatMessage {

    private String message;


    public ChatMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
