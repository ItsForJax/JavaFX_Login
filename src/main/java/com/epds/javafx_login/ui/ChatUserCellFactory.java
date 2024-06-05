package com.epds.javafx_login.ui;

import com.epds.javafx_login.entities.User;
import com.epds.javafx_login.ui.callbacks.ChatUserCellCallback;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import java.net.URL;

public class ChatUserCellFactory implements Callback<ListView<User>, ListCell<User>> {

    private final ChatUserCellCallback listener;

    public ChatUserCellFactory(ChatUserCellCallback listener) {
        this.listener = listener;
    }

    @Override
    public ListCell<User> call(ListView<User> userListView) {
        return new ChatUserListCell(userListView);
    }


    // ListCells for displaying a User
    private class ChatUserListCell extends ListCell<User> {

        private final AnchorPane root;
        private final ChatUserCellController controller;
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
                setOnMouseClicked(mouseEvent -> listener.invoke(user.getId()));

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
}
