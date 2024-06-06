package com.epds.javafx_login.database.chat;

import com.epds.javafx_login.entities.ChatMessage;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import io.reactivex.rxjava3.core.Single;

import java.sql.SQLException;
import java.util.List;

public class ChatMessageDao_Impl implements ChatMessageDao {

    private final Dao<ChatMessage, Integer> chatDao;

    public ChatMessageDao_Impl(ConnectionSource connectionSource) throws SQLException {
        this.chatDao = DaoManager.createDao(connectionSource, ChatMessage.class);
    }

    @Override
    public Single<ChatMessage> queryForId(int id) throws SQLException {
        return Single.fromCallable(() -> {
            QueryBuilder<ChatMessage, Integer> queryBuilder = chatDao.queryBuilder();
            Where<ChatMessage, Integer> where = queryBuilder.where();

            return where.eq(ChatMessage.ID_FIELD_NAME, id)
                    .queryForFirst();
        });
    }

    @Override
    public Single<List<ChatMessage>> queryForAll() throws SQLException {
        return Single.fromCallable(() -> {
            QueryBuilder<ChatMessage, Integer> queryBuilder = chatDao.queryBuilder();

            return queryBuilder.query();
        });
    }

    @Override
    public Single<Integer> create(ChatMessage chatMessage) throws SQLException {
        return Single.fromCallable(() -> {
            return chatDao.create(chatMessage);
        });
    }

    @Override
    public Single<Integer> update(ChatMessage chatMessage) throws SQLException {
        return Single.fromCallable(() -> {
            UpdateBuilder<ChatMessage, Integer> updateBuilder = chatDao.updateBuilder();

            updateBuilder.updateColumnValue(ChatMessage.MESSAGE_FIELD_NAME, chatMessage.getMessage())
                    .where()
                    .eq(ChatMessage.ID_FIELD_NAME, chatMessage.getId());

            return updateBuilder.update();
        });
    }

    @Override
    public Single<Integer> delete(ChatMessage chatMessage) throws SQLException {
        return Single.fromCallable(() -> {
            DeleteBuilder<ChatMessage, Integer> deleteBuilder = chatDao.deleteBuilder();

            deleteBuilder.where()
                    .eq(ChatMessage.ID_FIELD_NAME, chatMessage.getId());

            return deleteBuilder.delete();
        });
    }
}
