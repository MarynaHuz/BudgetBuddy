package com.softserve.dao;

import java.io.IOException;
import java.util.List;

public interface Dao<T> {

    void save(List<T> items) throws IOException;

    List<T> getAll() throws IOException;
}
