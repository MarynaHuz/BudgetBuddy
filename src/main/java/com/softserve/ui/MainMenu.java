package com.softserve.ui;

import java.util.Map;
import java.util.Scanner;

public class MainMenu implements Menu {

    private final Map<String, Menu> menus;
    private final Scanner scanner = new Scanner(System.in);

    private boolean exit;

    public MainMenu(Menu accountMenu, Menu transactionMenu) {

        this.menus = Map.of(
                "1", accountMenu,
                "2", transactionMenu,
                "3", this::exitApplication
        );
    }

    @Override
    public void show() {
        exit = false;
        while (!exit) {
            System.out.print("""
                    -------------------------------
                    │   💰💵 𝔹𝕌𝔻𝔾𝔼𝕋 𝔹𝕌𝔻𝔻𝕐 💵💰   │
                    -------------------------------
                    │ 1. 🏦 Manage Accounts        │
                    │ 2. 💳 Manage Transactions    │
                    │ 3. 🚪 Exit Application       │
                    -------------------------------
                    Choose an option:\s"""
            );
            String choice = scanner.nextLine();
            menus.getOrDefault(choice, this::invalidChoice).show();
        }
    }

    private void invalidChoice() {
        System.out.println("Invalid choice. Please try again.");
    }

    private void exitApplication() {
        System.out.println("Goodbye!");
        exit = true;
    }
}
