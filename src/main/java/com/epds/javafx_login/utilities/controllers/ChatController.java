package com.epds.javafx_login.utilities.controllers;

import com.epds.javafx_login.entities.ChatMessage;
import com.epds.javafx_login.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class ChatController {

    @FXML
    private ListView<User> user_list_view;

    @FXML
    private ListView<ChatMessage> message_list_view;

    @FXML
    private TextField chat_text;

    @FXML
    private Button send_chat_button;

    // Temporary list of users for testing, TODO: add database of users and chat messages
    private List<User> users;
    private List<ChatMessage> messages;


    private void openChatMessage(int userId) {
    }

}
