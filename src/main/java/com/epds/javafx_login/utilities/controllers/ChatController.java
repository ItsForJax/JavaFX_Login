package com.epds.javafx_login.utilities.controllers;

import com.epds.javafx_login.entities.ChatMessage;
import com.epds.javafx_login.entities.User;
import com.epds.javafx_login.ui.ChatMessageCellController;
import com.epds.javafx_login.ui.ChatUserCellController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

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
    @FXML
    private AnchorPane placeholder_pane;

    @FXML
    private Label profile_user_name;

    int currentId = -1;

    // Temporary list of users for testing, TODO: add database of users and chat messages
    private ObservableList<User> users = FXCollections.observableArrayList();
    private final HashMap<Integer, ObservableList<ChatMessage>> messages = new HashMap<>();

    @FXML
    private void initialize() {
        send_chat_button.setOnAction(evt -> addChatMessage());
        chat_message_pane.setVisible(false);

        user_list_view.setCellFactory(userListView -> new ChatUserListCell(userListView));
        chat_list_view.setCellFactory(chatMessageListView -> new ChatMessageListCell(chatMessageListView));

        fillWithDummyData();

        showUsers();
    }

    private void fillWithDummyData() {
        users = FXCollections.observableArrayList(
                new User(0, "Me, myself, and I"),
                new User(1, "Somebody else"),
                new User(2, "The 3rd Impact")
        );

        ObservableList<ChatMessage> msgs = FXCollections.observableArrayList(
                new ChatMessage("Beginning of Chat")
        );
        messages.put(0, msgs);

        ObservableList<ChatMessage> msgs1 = FXCollections.observableArrayList(
                new ChatMessage("Hey, it's me")
        );
        messages.put(1, msgs1);

        ObservableList<ChatMessage> msgs2 = FXCollections.observableArrayList(
                new ChatMessage("Who this????")
        );
        messages.put(2, msgs2);
    }

    @FXML
    private void showUsers() {
        user_list_view.setItems(users);
    }

    @FXML
    private void showChatMessages(int userId) {
        this.currentId = userId;

        // Show the chat pane and hide the placeholder text about selecting a user
        chat_message_pane.setVisible(true);
        placeholder_pane.setVisible(false);

        profile_user_name.setText(users.get(userId).getName());

        chat_list_view.setItems(messages.get(currentId));
    }

    @FXML
    private void addChatMessage() {
        String text = chat_text.getText();

        if (!text.isEmpty()) {
            messages.get(currentId).add(new ChatMessage(chat_text.getText()));
            chat_text.setText("");
            chat_list_view.scrollTo(chat_list_view.getItems().size() - 1);
        }
    }

    // ListCells for displaying Users and Chat Messages
    private class ChatUserListCell extends ListCell<User> {

        private AnchorPane root;
        private ChatUserCellController controller;
        private ListView<User> parent;

        public ChatUserListCell(ListView<User> parent) {
            this.parent = parent;
            // Adjusts the width of each cell based on its parent
            prefWidthProperty().bind(parent.widthProperty().subtract(29));

            try {
                final String CHAT_USER_CELL_FXML_PATH = "/com/epds/javafx_login/list_cells/chat_user_cell.fxml";

                URL fxml = getClass().getResource(CHAT_USER_CELL_FXML_PATH);
                FXMLLoader loader = new FXMLLoader(fxml);

                // Load the fxml file
                root = loader.load();
                controller = loader.getController();
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
                // Adds a listener to this cell
                setOnMouseClicked(mouseEvent -> showChatMessages(user.getId()));

                controller.chat_user_name.setText(user.getName());
                controller.chat_status.setFill(Paint.valueOf("red"));
                try {
                    URL resource = getClass().getResource("/com/epds/javafx_login/images/abstract.png");
                    Image image = new Image(resource.toExternalForm());
                    controller.chat_user_image.setImage(image);
                } catch (Exception e) {
                    System.out.println("Image not found");
                }

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
            // Adjusts the width of each cell based on its parent, subtracting due to padding and scrollbar
            // The number was guesstimated through trial and error
            prefWidthProperty().bind(parent.widthProperty().subtract(29));

            try {
                final String CHAT_MESSAGE_CELL_FXML_PATH = "/com/epds/javafx_login/list_cells/chat_message_cell.fxml";

                URL resource = getClass().getResource(CHAT_MESSAGE_CELL_FXML_PATH);
                FXMLLoader loader = new FXMLLoader(resource);

                // Load the fxml file
                root = loader.load();
                controller = loader.getController();
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
