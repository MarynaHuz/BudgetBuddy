package com.softserve.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    void save(T entity) throws IOException;
    Optional<T> findById(int id);
    List<T> getAll() throws IOException;
    void update(T entity);
    void delete(T entity);
}
