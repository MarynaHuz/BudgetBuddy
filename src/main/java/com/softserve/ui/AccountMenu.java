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
                "1", this::viewAccounts,
                "2", this::addAccount,
                "3", this::updateAccount,
                "4", this::makeInternalTransfer,
                "5", this::removeAccount,
                "6", this::exitMenu
        );
    }

    @Override
    public void show() {

        while (!exit) {
            System.out.print("""
                    ==== Manage Accounts =====
                    1. View Accounts
                    2. Add Account
                    3. Update Account
                    4. Make Internal Transfer
                    5. Remove Account
                    6. Back to Main Menu
                    Choose an option:\s"""
            );
            String action = scanner.nextLine();
            actions.getOrDefault(action, this::invalidChoice).run();
        }

    }

    private void viewAccounts() {
        accountController.listAll();
    }

    private void addAccount() {

    }

    private void updateAccount() {

    }

    private void makeInternalTransfer() {

    }

    private void removeAccount() {

    }

    private void exitMenu() {

    }

    private void invalidChoice() {
        System.out.println("Invalid choice. Please try again.");
    }
}
