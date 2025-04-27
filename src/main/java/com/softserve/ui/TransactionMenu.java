package com.softserve.ui;

import com.softserve.controllers.TransactionController;

import java.util.Map;
import java.util.Scanner;

public class TransactionMenu implements Menu {

    private final TransactionController transactionController;
    private final Scanner scanner = new Scanner(System.in);

    private final Map<String, Runnable> actions;
    private boolean exit = false;

    public TransactionMenu(TransactionController transactionController) {
        this.transactionController = transactionController;


        this.actions = Map.of(
                "1", this::addTransaction,
                "2", this::findTransactionById,
                "3", this::viewTransactions,
                "4", this::updateTransaction,
                "5", this::removeTransaction,
                "6", this::exitMenu
        );
    }

    @Override
    public void show() {
        while (!exit) {
            System.out.print("""
                    --- Manage Transactions ---
                    1. Add Transaction
                    2. Find Transaction by ID
                    3. View Transactions
                    4. Update Transaction
                    5. Remove Transaction
                    6. Back to Main Menu
                    Choose an option:\s"""
            );

            String choice = scanner.nextLine();
            actions.getOrDefault(choice, this::invalidChoice).run();
        }
    }

    private void addTransaction() {

    }

    private void findTransactionById() {
        System.out.print("Enter the transaction ID: ");
        String id = scanner.nextLine();
        transactionController.findById(id);
    }

    private void viewTransactions() {
        transactionController.listAll();
    }

    private void updateTransaction() {

    }

    private void removeTransaction() {

    }

    private void exitMenu() {
        System.out.println("Returning to Main Menu...");
        System.out.println("-".repeat(31));
        exit = true;
    }

    private void invalidChoice() {
        System.out.println("Invalid choice. Please try again.");
    }
}
