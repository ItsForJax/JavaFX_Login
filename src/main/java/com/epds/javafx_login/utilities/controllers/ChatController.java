package com.epds.javafx_login.utilities.controllers;

import com.epds.javafx_login.entities.ChatMessage;
import com.epds.javafx_login.entities.User;
import com.epds.javafx_login.ui.ChatMessageCellController;
import com.epds.javafx_login.ui.ChatUserCellController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
    private final ObservableList<User> users = FXCollections.observableArrayList();
    private final HashMap<Integer, ObservableList<ChatMessage>> messages = new HashMap<>();

    @FXML
    private void initialize() {
        send_chat_button.setOnAction(evt -> addChatMessage());

        user_list_view.setCellFactory(userListView -> new ChatUserListCell(userListView));
        chat_list_view.setCellFactory(chatMessageListView -> new ChatMessageListCell(chatMessageListView));

        fillWithDummyData();

        showUsers();
        showChatMessages(0);
    }

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

    // ListCells for displaying Users and Chat Messages
    private class ChatUserListCell extends ListCell<User> {
        private AnchorPane root;
        private ChatUserCellController controller;
        private ListView<User> parent;

        public ChatUserListCell(ListView<User> parent) {
            this.parent = parent;

            try {
                final String CHAT_USER_CELL_FXML_PATH = "/com/epds/javafx_login/list_cells/chat_user_cell.fxml";

                URL fxml = getClass().getResource(CHAT_USER_CELL_FXML_PATH);
                FXMLLoader loader = new FXMLLoader(fxml);

                // Load the fxml file
                root = loader.load();
                controller = loader.getController();

                // Adjusts the width of each cell based on its parent, subtracting 16 due to padding
                root.prefWidthProperty().bind(parent.widthProperty().subtract(16));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void updateItem(User user, boolean empty) {
            super.updateItem(user, empty);

            if (empty || user == null) {
                setGraphic(null);
            }
            else {
                controller.chat_user_name.setText(user.getName());
                setGraphic(root);
            }
        }
    }

    private class ChatMessageListCell extends ListCell<ChatMessage> {

        private AnchorPane root;
        private ChatMessageCellController controller;
        private ListView<ChatMessage> parent;

        public ChatMessageListCell(ListView<ChatMessage> parent) {
            this.parent = parent;

            try {
                final String CHAT_MESSAGE_CELL_FXML_PATH = "/com/epds/javafx_login/list_cells/chat_message_cell.fxml";

                URL resource = getClass().getResource(CHAT_MESSAGE_CELL_FXML_PATH);
                FXMLLoader loader = new FXMLLoader(resource);

                // Load the fxml file
                root = loader.load();
                controller = loader.getController();

                // Adjusts the width of each cell based on its parent, subtracting 16 due to padding
                root.prefWidthProperty().bind(parent.widthProperty().subtract(16));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void updateItem(ChatMessage message, boolean empty) {
            super.updateItem(message, empty);

            if (empty || message == null) {
                setGraphic(null);
            }
            else {
                controller.chat_message_text.setText(message.getMessage());
                setGraphic(root);
            }
        }
    }

}
