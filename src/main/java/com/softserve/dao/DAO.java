package com.softserve.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    void save(T entity);
    Optional<T> read(String entity);
    List<T> getAll();
    void update(T entity);
    void delete(T entity);
}
