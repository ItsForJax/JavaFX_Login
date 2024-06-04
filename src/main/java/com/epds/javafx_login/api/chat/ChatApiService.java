package com.epds.javafx_login.api.chat;

import com.epds.javafx_login.api.chat.model.ChatItem;
import com.epds.javafx_login.api.chat.model.Ticket;
import com.epds.javafx_login.api.chat.model.TicketResponse;
import com.epds.javafx_login.entities.User;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ChatApiService {

    @GET("/api/chat_messages")
    Observable<ChatItem> getChatMessages(
            @Body User sender,
            @Body User receiver);

    @POST("/api/add_new_conversation")
    Completable addNewConversation(
            @Body User sender,
            @Body User receiver);

    @GET("/tickets")
    Observable<Response<TicketResponse>> getTickets();
}
