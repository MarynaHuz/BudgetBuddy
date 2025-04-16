package com.softserve.services;

import java.util.List;
import java.util.Optional;

public interface Service <T>{

    void save(T entity);
    Optional<T> read(String id);
    List<T> getAll();
    void update(T entity);
    void delete(String id);
}
