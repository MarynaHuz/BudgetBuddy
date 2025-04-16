package com.softserve.services;

import java.util.List;
import java.util.Optional;

public interface Service <T>{

    void create(T entity);
    Optional<T> findById(String id);
    List<T> listAll();
    void update(T entity);
    void removeById(String id);
}
