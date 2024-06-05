package com.epds.javafx_login.utilities.controllers;

import com.epds.javafx_login.api.chat.ChatApiClient;
import com.epds.javafx_login.api.chat.ChatApiService;
import com.epds.javafx_login.api.chat.model.*;
import com.epds.javafx_login.entities.ChatMessage;
import com.epds.javafx_login.entities.User;
import com.epds.javafx_login.ui.ChatMessageCellController;
import com.epds.javafx_login.ui.ChatUserCellController;
import com.epds.javafx_login.utilities.DataService;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.beans.value.ObservableMapValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import retrofit2.Response;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ChatController {

    // UI elements
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

    @FXML
    private Button new_chat_user_button;
    @FXML
    private TextField new_chat_user_text;

    // Variables
    int userId = 0;         // Stores a temporary user id for dummy data
    int currentId = -1;     // Stores the id of the user selected to show the chat messages related to that user

    // API Handler
    private ChatApiService apiClient = ChatApiClient.getAPIClient().create(ChatApiService.class);
    //
    private DataService dataService = DataService.getInstance();

    // Temporary list of users for testing, TODO: add database of users and chat messages
    private final ObservableList<User> users = FXCollections.observableArrayList();
    private final HashMap<Integer, ObservableList<ChatMessage>> messages = new HashMap<>();

    @FXML
    private void initialize() {
        send_chat_button.setOnAction(evt -> addChatMessage());
        new_chat_user_button.setOnAction(evt -> addNewChatUser());
        chat_message_pane.setVisible(false);

        user_list_view.setCellFactory(userListView -> new ChatUserListCell(userListView));
        chat_list_view.setCellFactory(chatMessageListView -> new ChatMessageListCell(chatMessageListView));

        //fillWithDummyData();
        //fillWithDataFromAPI();

        dataService.fetchData();

        showUsers();
    }

    // Fill the users and messages with API data
    private void fillWithDataFromAPI() {
        // Async method of getting tickets
        // TODO: add a CompositeDisposable to prevent memory leaks
        apiClient.getTickets()
                .observeOn(Schedulers.io())
                .take(1)
                .subscribe(
                        response -> {
                            if (response.isSuccessful()) {
                                List<Ticket> tickets = response.body().getTickets();

                                // Print each ticket into the console
                                for (Ticket t : tickets) {
                                    System.out.println(t);

                                    // Add each ticket as user and the conversation as chat messages
                                    addDummyUser(t.getId() - 1, t.getSender());

                                    // Add each ChatItem as a ChatMessage
                                    for (ChatItem chatItem : t.getConversation())
                                        messages.get(t.getId() - 1).add(new ChatMessage(chatItem.getMessage()));
                                }
                            }
                        },
                        error -> {
                            throw new RuntimeException(error);
                        }
                );
    }

    // Fill the users and messages with dummy data
    private void fillWithDummyData() {
        addDummyUser(userId++, "Me, myself, and I");
        addDummyUser(userId++, "Somebody else");
        addDummyUser(userId++, "The 3rd Impact");

        messages.get(0).add(new ChatMessage("Beginning of Chat"));
        messages.get(1).add(new ChatMessage("Hey, it's me"));
        messages.get(2).add(new ChatMessage("Who this????"));
    }

    @FXML
    private void addNewChatUser() {
        String new_username = new_chat_user_text.getText();

        if (!new_username.isEmpty()) {
            addDummyUser(userId, new_username);

            userId += 1;
        }
    }

    // Adds a new dummy user with a given id and username
    @FXML
    private void addDummyUser(int id, String name) {
        users.add(new User(id, name));
        messages.put(id, FXCollections.observableArrayList());
    }

    @FXML
    private void showUsers() {
        // Display the users data
        user_list_view.setItems(dataService.getUsers());
    }

    @FXML
    private void showChatMessages(int userId) {
        this.currentId = userId;

        System.out.println(userId);

        // Show the chat pane and hide the placeholder text about selecting a user
        chat_message_pane.setVisible(true);
        placeholder_pane.setVisible(false);

        // Get the username given the id and display it in the top bar chat pane
        profile_user_name.setText(dataService.getUsers().get(currentId).getName());
        // Show the chat messages related to the currently selected user id
        chat_list_view.setItems(dataService.getChatMessages(currentId));
    }

    @FXML
    private void addChatMessage() {
        String text = chat_text.getText();

        if (!text.isEmpty()) {
//            messages.get(currentId).add(new ChatMessage(text));

            // Creating a new ticket
            Date ticketTimestamp = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            String formattedTicketTimestamp = dateFormat.format(ticketTimestamp);

            MessageRequest messageRequest = new MessageRequest(
                    formattedTicketTimestamp,
                    "user123",
                    text,
                    false
            );

            apiClient.sendMessage(String.valueOf(currentId + 1), messageRequest)
                    .subscribe(
                            response -> {
                                if (response.isSuccessful()) {
                                    TicketResponse updatedTicket = response.body();
                                    // Handle the updated ticket
                                } else {
                                    // Handle the error
                                }
                            },
                            error -> {
                                // Handle the error
                            }
                    );

            chat_text.setText("");
            chat_list_view.scrollTo(chat_list_view.getItems().size() - 1);

        }
    }

    // ListCells for displaying a User
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
