package com.softserve.ui;

import java.util.Map;
import java.util.Scanner;

public class MainMenu implements Menu {

    private final Map<String, Menu> menus;
    private final Scanner scanner = new Scanner(System.in);

    private boolean exit = false;

    public MainMenu(Menu accountMenu, Menu transactionMenu) {

        this.menus = Map.of(
                "0", this::exitApplication,
                "1", accountMenu,
                "2", transactionMenu
        );
    }

    @Override
    public void show() {
        while (!exit) {
            displayMenuOptions();
            String choice = scanner.nextLine();
            menus.getOrDefault(choice, this::invalidChoice).show();
        }
    }

    private static void displayMenuOptions() {
        System.out.print("""
                
                --------------------------------
                │       💰 𝔹𝕌𝔻𝔾𝔼𝕋 𝔹𝕌𝔻𝔻𝕐        │
                --------------------------------
                │ 0. 🚪 Exit Application       │
                │ 1. 🏦 Manage Accounts        │
                │ 2. 💳 Manage Transactions    │
                --------------------------------
                Choose an option:\s"""
        );
    }

    private void invalidChoice() {
        System.out.print("""
                --------------------------------
                │        ⚠️  WARNING           │
                --------------------------------
                │ ❌ Invalid choice.           │
                │ 🔄 Please try again.         │
                │ ⌨️ Enter a number (0-2).     │
                --------------------------------
                """
        );
    }

    private void exitApplication() {
        System.out.print("""
                --------------------------------
                │       👋 𝕋ℍ𝔸ℕ𝕂 𝕐𝕆𝕌!          │
                --------------------------------
                │    See you next time at      │
                │        𝔹𝕌𝔻𝔾𝔼𝕋 𝔹𝕌𝔻𝔻𝕐!         │
                --------------------------------
                """
        );
        exit = true;
    }
}
