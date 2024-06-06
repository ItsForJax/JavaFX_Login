package com.epds.javafx_login.utilities;

import com.epds.javafx_login.Main;
import com.epds.javafx_login.api.chat.ChatApiClient;
import com.epds.javafx_login.api.chat.ChatApiService;
import com.epds.javafx_login.api.chat.model.ChatItem;
import com.epds.javafx_login.api.chat.model.MessageRequest;
import com.epds.javafx_login.api.chat.model.Ticket;
import com.epds.javafx_login.api.chat.model.TicketResponse;
import com.epds.javafx_login.entities.ChatMessage;
import com.epds.javafx_login.entities.User;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class ChatDataService {

    private static volatile ChatDataService INSTANCE = null;

    private final ObservableList<Ticket> fetchedTickets = FXCollections.observableArrayList();
    private final ObservableList<User> users = FXCollections.observableArrayList();
    private final HashMap<Integer, ObservableList<ChatMessage>> messages = new HashMap<>();

    private boolean dataFetched = false;

    ChatApiService apiClient = ChatApiClient.getAPIClient().create(ChatApiService.class);

    private ChatDataService() {
        // Private constructor to enforce singleton pattern
    }

    public static ChatDataService getInstance() {
        if (INSTANCE == null) {
            synchronized (ChatDataService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ChatDataService();
                }
            }
        }

        return INSTANCE;
    }

    // Fill the users and messages with API data
    public void fetchData() {
        if (!dataFetched) {
            // Async method of getting tickets
            Disposable d = apiClient.getTickets()
                    .observeOn(Schedulers.single())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            response -> {
                                if (response.isSuccessful()) {
                                    fetchedTickets.addAll(response.body().getTickets());
                                    processTickets();
                                    dataFetched = true;
                                }
                            },
                            error -> {
                                throw new RuntimeException(error);
                            }
                    );

            // To prevent memory leaks
            Main.getCompositeDisposable().add(d);
        }
    }

    private void processTickets() {
        // Clear existing data to prevent duplication
        users.clear();
        messages.clear();

        for (Ticket t : fetchedTickets) {
            System.out.println(t);

            // Add each ticket as a user and the conversation as chat messages
            addUser(t.getId() - 1, t.getSender());

            // Add each ChatItem as a ChatMessage
            for (ChatItem chatItem : t.getConversation()) {
                messages.get(t.getId() - 1).add(new ChatMessage(chatItem.getMessage()));
            }
        }
    }

    private void addUser(int id, String name) {
        users.add(new User(id, name));
        messages.put(id, FXCollections.observableArrayList());
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public ObservableList<ChatMessage> getChatMessages(int userId) {
        return messages.get(userId);
    }

    public Observable<Response<TicketResponse>> addChatMessage(String text) {
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

        return apiClient.sendMessage(String.valueOf(UUID.randomUUID()), messageRequest);
    }
}