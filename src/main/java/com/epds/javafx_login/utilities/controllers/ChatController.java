package com.epds.javafx_login.utilities.controllers;

import com.epds.javafx_login.Main;
import com.epds.javafx_login.api.chat.ChatApiClient;
import com.epds.javafx_login.api.chat.ChatApiService;
import com.epds.javafx_login.api.chat.model.*;
import com.epds.javafx_login.entities.ChatMessage;
import com.epds.javafx_login.entities.User;
import com.epds.javafx_login.ui.ChatMessageCellFactory;
import com.epds.javafx_login.ui.ChatUserCellFactory;
import io.reactivex.rxjava3.disposables.Disposable;
import com.epds.javafx_login.utilities.ChatDataService;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

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
    private final ChatDataService dataService = ChatDataService.getInstance();

    // Temporary list of users for testing, TODO: add database of users and chat messages
    private final ObservableList<User> users = FXCollections.observableArrayList();
    private final HashMap<Integer, ObservableList<ChatMessage>> messages = new HashMap<>();

    @FXML
    private void initialize() {
        send_chat_button.setOnAction(evt -> addChatMessage());
        new_chat_user_button.setOnAction(evt -> addNewChatUser());

        // Hide the chat message pane and show the pane that asks to select a user
        chat_message_pane.setVisible(false);
        placeholder_pane.setVisible(true);

        user_list_view.setCellFactory(new ChatUserCellFactory(id -> {
            showChatMessages(id);
            //showDummyChatMessages(id);
        }));
        chat_list_view.setCellFactory(new ChatMessageCellFactory());

        //fillWithDummyData();
        dataService.fetchData();

        //showDummyUsers();
        showUsers();
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
        // Show the chat messages related to the currently selected user id and automatically scroll to bottom
        chat_list_view.setItems(dataService.getChatMessages(currentId));
        chat_list_view.scrollTo(chat_list_view.getItems().size() - 1);
    }

    @FXML
    private void addChatMessage() {
        String text = chat_text.getText();

        if (!text.isEmpty()) {
                Disposable d = dataService.addChatMessage(text)
                        .observeOn(Schedulers.single())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                response -> {
                                    if (response.isSuccessful()) {
                                        chat_text.setText("");
                                        chat_list_view.scrollTo(chat_list_view.getItems().size() - 1);

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

            // To prevent memory leak
            Main.getCompositeDisposable().add(d);
        }
    }


    // For testing purposes
    @FXML
    private void showDummyUsers() {
        user_list_view.setItems(users);
    }

    @FXML
    private void showDummyChatMessages(int id) {
        this.currentId = id;

        // Show the chat pane and hide the placeholder text about selecting a user
        chat_message_pane.setVisible(true);
        placeholder_pane.setVisible(false);

        // Get the username given the id and display it in the top bar chat pane
        profile_user_name.setText(users.get(currentId).getName());
        // Show the chat messages related to the currently selected user id and automatically scroll to bottom
        chat_list_view.setItems(messages.get(currentId));
        chat_list_view.scrollTo(chat_list_view.getItems().size() - 1);
    }

}
