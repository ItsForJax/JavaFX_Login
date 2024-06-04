package com.epds.javafx_login.api.chat.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ticket {

    @SerializedName("ticket_id")
    private int id;

    @SerializedName("conversation")
    private List<ChatItem> chatItems;

    @SerializedName("status")
    private String status;

    @SerializedName("support")
    private String support_email;
}
