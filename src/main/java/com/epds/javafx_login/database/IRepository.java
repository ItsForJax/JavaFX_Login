package com.epds.javafx_login.database;

import io.reactivex.rxjava3.core.Single;

import java.util.List;

// Generic Repository interface for basic database CRUD
public interface IRepository<T> {

    Single<T> findById(int id);
    Single<List<T>> getAll();
    Single<Integer> create(T entity);
    Single<Integer> update(T entity);
    Single<Integer> delete(T entity);
}
