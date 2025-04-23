package com.softserve.dao;

import java.io.IOException;
import java.util.List;

public interface Dao<T> {

    void save(T entity) throws IOException;
    List<T> getAll() throws IOException;
    void updateById(int id);
    void deleteById(int id) throws IOException;
}
