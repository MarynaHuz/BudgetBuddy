package com.softserve.ui;

import com.softserve.controllers.AccountController;

import java.util.Map;
import java.util.Scanner;

public class AccountMenu implements Menu {

    private final AccountController accountController;
    private final Scanner scanner = new Scanner(System.in);

    private final Map<String, Runnable> actions;

    private boolean exit;

    public AccountMenu(AccountController accountController) {
        this.accountController = accountController;
        this.actions = Map.of(
                "0", this::exitMenu,
                "1", this::addAccount,
                "2", this::findAccountById,
                "3", this::viewAccounts,
                "4", this::updateAccount,
                "5", this::removeAccount,
                "6", this::makeInternalTransfer
        );
    }

    @Override
    public void show() {
        while (!exit) {
            System.out.print("""
        --------------------------------
        │    🏦 𝕄𝔸ℕ𝔸𝔾𝔼 𝔸ℂℂ𝕆𝕌ℕ𝕋𝕊        │
        --------------------------------
        │ 0. 🔙 Back to Main Menu      │
        │ 1. ➕ Add Account            │
        │ 2. 🔍 Find Account by ID     │
        │ 3. 📋 View Accounts          │
        │ 4. 📝 Update Account         │
        │ 5. ❌ Remove Account         │
        │ 6. 💸 Make Internal Transfer │
        --------------------------------
        Choose an option:\s"""
            );
            String action = scanner.nextLine();
            actions.getOrDefault(action, this::invalidChoice).run();
        }

    }

    private void addAccount() {

    }

    private void findAccountById() {

    }

    private void viewAccounts() {
        accountController.listAll();
    }

    private void updateAccount() {

    }

    private void makeInternalTransfer() {

    }

    private void removeAccount() {

    }

    private void exitMenu() {
        System.out.println("Returning to Main Menu...");
        System.out.println("-".repeat(32));
        exit = true;
    }

    private void invalidChoice() {
        System.out.println("Invalid choice. Please try again.");
    }
}
