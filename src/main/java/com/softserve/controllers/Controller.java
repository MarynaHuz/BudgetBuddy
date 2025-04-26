package com.softserve.controllers;

import java.util.List;
import java.util.Map;

public interface Controller {

    void create(List<String> account);

    void findById(String id);

    void listAll();

    void update(Map<String, List<String>> entity);

    void delete(String id);
}
