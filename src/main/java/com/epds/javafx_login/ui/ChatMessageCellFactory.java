package com.epds.javafx_login.ui;

import com.epds.javafx_login.models.ChatMessage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.net.URL;

public class ChatMessageCellFactory implements Callback<ListView<ChatMessage>, ListCell<ChatMessage>> {

    @Override
    public ListCell<ChatMessage> call(ListView<ChatMessage> userListView) {
        return new ChatMessageListCell(userListView);
    }

    
    // List Cell for displaying a ChatMessage
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

                URL fxml = getClass().getResource(CHAT_MESSAGE_CELL_FXML_PATH);
                FXMLLoader loader = new FXMLLoader(fxml);

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
