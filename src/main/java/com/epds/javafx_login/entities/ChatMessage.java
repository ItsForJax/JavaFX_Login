package com.epds.javafx_login.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ChatMessages")
public class ChatMessage {

    // For QueryBuilder to find the names
    public static final String MESSAGE_FIELD_NAME = "message";

    @DatabaseField(id = true)
    private long id;
    @DatabaseField(columnName = MESSAGE_FIELD_NAME, canBeNull = false)
    private String message;

    public ChatMessage() {
        // ORMLite needs a no-arg constructor
    }

    public ChatMessage(String message) {
        this.id = 0;
        this.message = message;
    }

    public ChatMessage(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
