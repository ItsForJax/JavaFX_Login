package com.epds.javafx_login.api.chat.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ticket {

    @SerializedName("ticket_id")
    private int id;

    @SerializedName("conversation")
    private List<ChatItem> conversation;

    @SerializedName("status")
    private String status;

    @SerializedName("sender")
    private String sender;

    @SerializedName("support")
    private String support_email;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ChatItem> getConversation() {
        return conversation;
    }

    public void setConversation(List<ChatItem> conversation) {
        this.conversation = conversation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSupport_email() {
        return support_email;
    }

    public void setSupport_email(String support_email) {
        this.support_email = support_email;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", conversation=" + conversation +
                ", status='" + status + '\'' +
                ", sender='" + sender + '\'' +
                ", support_email='" + support_email + '\'' +
                "}";
    }
}

