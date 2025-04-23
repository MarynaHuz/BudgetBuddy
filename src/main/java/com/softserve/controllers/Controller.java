package com.softserve.controllers;

public interface Controller<T> {

    void create(T entity);

    void findById(String id);

    void listAll();

    void update(T entity);

    boolean delete(String id);
}
