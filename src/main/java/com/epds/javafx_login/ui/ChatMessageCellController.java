package com.epds.javafx_login.ui;

import com.epds.javafx_login.entities.ChatMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ChatMessageCellController {

    @FXML
    private Label chat_message_text;

    public String getChatMessageText() {
        return chat_message_text.getText();
    }

    public void setChatMessageText(ChatMessage chatMessage) {
        this.chat_message_text.setText(chatMessage.getMessage());
    }
}
