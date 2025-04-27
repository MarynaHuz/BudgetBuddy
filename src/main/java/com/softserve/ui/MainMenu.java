package com.softserve.ui;

import java.util.Map;
import java.util.Scanner;

public class MainMenu implements Menu{

    private final Map<String, Menu> menus;
    private final Scanner scanner = new Scanner(System.in);

    private boolean exit;

    public MainMenu(Menu accountMenu, Menu transactionMenu) {

        this.menus = Map.of(
                "1", accountMenu,
                "2", transactionMenu
        );
    }


    @Override
    public void show() {

    }
}
