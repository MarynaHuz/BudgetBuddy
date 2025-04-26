package com.softserve.controllers;

import java.util.List;
import java.util.Map;

public interface Controller<T> {

    void create(List<T> account);

    void findById(T id);

    void listAll();

    void update(Map<T, List<T>> entity);

    void delete(T id);
}
