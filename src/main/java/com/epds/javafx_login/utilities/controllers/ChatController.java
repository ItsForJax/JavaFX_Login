package com.epds.javafx_login.utilities.controllers;

import com.epds.javafx_login.entities.ChatMessage;
import com.epds.javafx_login.entities.User;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.HashMap;
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
    private ObservableList<User> users = FXCollections.emptyObservableList();
    private HashMap<Integer, ObservableList<ChatMessage>> messages = new HashMap<>();

    public ChatController() {
        fillWithDummyData();

        showUsers();
        showChatMessages(0);
    }

    private void fillWithDummyData() {
        //users.add(new User(0, "Me, myself, and I"));

        ObservableList<ChatMessage> msgs = FXCollections.observableArrayList(
                new ChatMessage("TEST")
        );

        messages.put(0, msgs);
    }

    private void showUsers() {
        //user_list_view.setItems(users);
    }

    private void showChatMessages(int userId) {
        //message_list_view.setItems(messages.get(userId));
    }

    @FXML
    protected void addChatMessage() {
        //messages.get(0).add(new ChatMessage(chat_text.getText()));
    }

}
