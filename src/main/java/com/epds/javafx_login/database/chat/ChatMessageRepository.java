package com.epds.javafx_login.database.chat;

import com.epds.javafx_login.database.IRepository;
import com.epds.javafx_login.entities.ChatMessage;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class ChatMessageRepository implements IRepository<ChatMessage> {

    private Dao<ChatMessage, Integer> chatDao;

    public ChatMessageRepository(ConnectionSource connectionSource) throws SQLException {
        chatDao = DaoManager.createDao(connectionSource, ChatMessage.class);
    }

    @Override
    public ChatMessage findById(int id) {
        try {
            return chatDao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ChatMessage> getAll() {
        try {
            return chatDao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean create(ChatMessage entity) {
        try {
            return chatDao.create(entity) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(ChatMessage entity) {
        try {
            return chatDao.update(entity) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(ChatMessage entity) {
        try {
            return chatDao.delete(entity) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
