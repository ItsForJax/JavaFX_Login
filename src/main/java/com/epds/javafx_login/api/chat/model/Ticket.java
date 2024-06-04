package com.epds.javafx_login.api.chat.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ticket {

    @SerializedName("ticket_id")
    private int id;
    public int getId() {
        return id;
    }

    @SerializedName("conversation")
    private List<ChatItem> chatItems;
    public List<ChatItem> getConversation(){return chatItems;}

    @SerializedName("status")
    private String status;

    @SerializedName("sender")
    private String sender;
    public String getSender() {
        return sender;
    }

    @SerializedName("support")
    private String support_email;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", chatItems=" + chatItems +
                ", status='" + status + '\'' +
                ", sender='" + sender + '\'' +
                ", support_email='" + support_email + '\'' +
                "}";
    }
}

