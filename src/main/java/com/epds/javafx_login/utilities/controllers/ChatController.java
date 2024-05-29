package com.epds.javafx_login.utilities.controllers;

import com.epds.javafx_login.entities.ChatMessage;
import com.epds.javafx_login.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;

public class ChatController {

    @FXML
    private ListView<User> user_list_view;

    @FXML
    private ListView<ChatMessage> chat_list_view;

    @FXML
    private TextField chat_text;

    @FXML
    private Button send_chat_button;

    @FXML
    private AnchorPane chat_message_pane;

    int currentId = -1;

    // Temporary list of users for testing, TODO: add database of users and chat messages
    private ObservableList<User> users = FXCollections.observableArrayList();
    private HashMap<Integer, ObservableList<ChatMessage>> messages = new HashMap<>();

    @FXML
    private void initialize() {
        fillWithDummyData();

        showUsers();
        showChatMessages(0);
    }

    @FXML
    private void fillWithDummyData() {
        users.add(new User(0, "Me, myself, and I"));

        ObservableList<ChatMessage> msgs = FXCollections.observableArrayList(
                new ChatMessage("TEST")
        );

        messages.put(0, msgs);
    }

    @FXML
    private void showUsers() {
        user_list_view.setItems(users);
    }

    @FXML
    private void showChatMessages(int userId) {
        this.currentId = userId;

        chat_message_pane.setVisible(true);
        chat_text.setEditable(true);

        chat_list_view.setItems(messages.get(userId));
    }

    @FXML
    private void addChatMessage() {
        String text = chat_text.getText();

        if (!text.isEmpty())
            messages.get(currentId).add(new ChatMessage(chat_text.getText()));
    }

}
