package com.softserve.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface Service <T>{

    void create(T entity) throws IOException;
    Optional<T> findById(int id) throws IOException;
    List<T> listAll() throws IOException;
    void update(T entity);
    Optional<T> removeById(int id) throws IOException;

    default boolean existById(int id) throws IOException {
       return (findById(id).isPresent());
    }
}
