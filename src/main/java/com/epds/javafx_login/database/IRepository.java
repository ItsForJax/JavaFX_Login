package com.epds.javafx_login.database;

import java.util.List;

// Generic Repository interface for basic database CRUD
public interface IRepository<T> {

    T findById(int id);
    List<T> getAll();
    boolean create(T entity);
    boolean update(T entity);
    boolean delete(T entity);
}
