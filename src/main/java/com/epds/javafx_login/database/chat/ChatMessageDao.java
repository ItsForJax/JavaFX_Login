package com.epds.javafx_login.database.chat;

import com.epds.javafx_login.entities.ChatMessage;
import io.reactivex.rxjava3.core.Single;

import java.sql.SQLException;
import java.util.List;

public interface ChatMessageDao {

    Single<ChatMessage> queryForId(int id) throws SQLException;
    Single<List<ChatMessage>> queryForAll() throws SQLException;
    Single<Integer> create(ChatMessage chatMessage) throws SQLException;
    Single<Integer> update(ChatMessage chatMessage) throws SQLException;
    Single<Integer> delete(ChatMessage chatMessage) throws SQLException;

}
