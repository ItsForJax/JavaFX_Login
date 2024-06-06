package com.epds.javafx_login.database.chat;

import com.epds.javafx_login.database.IRepository;
import com.epds.javafx_login.entities.ChatMessage;
import com.j256.ormlite.support.ConnectionSource;
import io.reactivex.rxjava3.core.Single;

import java.sql.SQLException;
import java.util.List;

public class ChatMessageRepository implements IRepository<ChatMessage> {

    // Make this class a singleton
    private static volatile ChatMessageRepository INSTANCE = null;
    private final ChatMessageDao_Impl chatDao;

    private ChatMessageRepository(ConnectionSource connectionSource) throws SQLException {
        chatDao = new ChatMessageDao_Impl(connectionSource);
    }

    public static void initialize(ConnectionSource connectionSource) throws SQLException {
        if (INSTANCE == null) {
            synchronized (ChatMessageRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ChatMessageRepository(connectionSource);
                }
            }
        }
    }

    public static ChatMessageRepository getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("ChatMessageRepository must be initialized!");
        }

        return INSTANCE;
    }

    @Override
    public Single<ChatMessage> findById(int id) {
        try {
            return chatDao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Single<List<ChatMessage>> getAll() {
        try {
            return chatDao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Single<Integer> create(ChatMessage entity) {
        try {
            return chatDao.create(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Single<Integer> update(ChatMessage entity) {
        try {
            return chatDao.update(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Single<Integer> delete(ChatMessage entity) {
        try {
            return chatDao.delete(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
