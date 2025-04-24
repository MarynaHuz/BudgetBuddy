package com.softserve.controllers;

import java.util.List;

public interface Controller<T> {

    void create(List<String> account);

    void findById(String id);

    void listAll();

    void update(T entity);

    void delete(String id);
}
