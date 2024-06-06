package com.epds.javafx_login.database.chat;

import com.epds.javafx_login.entities.ChatMessage;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import io.reactivex.rxjava3.core.Single;

import java.sql.SQLException;
import java.util.List;

public class ChatMessageDaoImpl implements ChatMessageDao {

    private Dao<ChatMessage, Integer> chatDao = null;

    public ChatMessageDaoImpl(ConnectionSource connectionSource) throws SQLException {
        this.chatDao = DaoManager.createDao(connectionSource, ChatMessage.class);
    }

    @Override
    public Single<ChatMessage> queryForId(int id) throws SQLException {
        return Single.fromCallable(() -> chatDao.queryForId(id));
    }

    @Override
    public Single<List<ChatMessage>> queryForAll() throws SQLException {
        return Single.fromCallable(() -> chatDao.queryForAll());
    }

    @Override
    public Single<Integer> create(ChatMessage chatMessage) throws SQLException {
        return Single.fromCallable(() -> chatDao.create(chatMessage));
    }

    @Override
    public Single<Integer> update(ChatMessage chatMessage) throws SQLException {
        return Single.fromCallable(() -> chatDao.update(chatMessage));
    }

    @Override
    public Single<Integer> delete(ChatMessage chatMessage) throws SQLException {
        return Single.fromCallable(() -> chatDao.delete(chatMessage));
    }
}
