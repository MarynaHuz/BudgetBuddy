package com.softserve.ui;

import com.softserve.controllers.TransactionController;

import java.util.List;
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
                "0", this::exitMenu,
                "1", this::addTransaction,
                "2", this::findTransactionById,
                "3", this::viewTransactions,
                "4", this::updateTransaction,
                "5", this::removeTransactionById
        );
    }

    @Override
    public void show() {
        while (!exit) {
            displayMenuOptions();
            String choice = scanner.nextLine();
            actions.getOrDefault(choice, this::invalidChoice).run();
        }
    }

    private void addTransaction() {
        List<String> prompts = List.of(
                "Enter Account ID:",
                "Enter Transaction Category:",
                "Enter Transaction Date (dd-MM-yyyy):",
                "Enter Transaction Amount:"
        );

        List<String> transactionData = prompts.stream()
                .map(prompt -> {
                    System.out.println(prompt);
                    return scanner.nextLine().trim();
                })
                .toList();

        transactionController.create(transactionData);
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
        System.out.println("""
                --------------------------------
                │       ⚠️ ℕ𝕆𝕋𝕀ℂ𝔼              │
                --------------------------------
                │   Transaction updates are    │
                │ currently under development. │
                │ For now, please delete the   │
                │ transaction and create a     │
                │ new one with your changes.   │
                --------------------------------
                """);
    }

    private void removeTransactionById() {
        System.out.print("Enter the transaction ID: ");
        String id = scanner.nextLine();
        transactionController.delete(id);
    }

    private static void displayMenuOptions() {
        System.out.print("""
                --------------------------------
                │    💳 𝕄𝔸ℕ𝔸𝔾𝔼 𝕋ℝ𝔸ℕ𝕊𝔸ℂ𝕋𝕀𝕆ℕ𝕊    │
                --------------------------------
                │ 0. 🔙 Back to Main Menu      │
                │ 1. ➕ Add Transaction        │
                │ 2. 🔍 Find Transaction by ID │
                │ 3. 📊 View Transactions      │
                │ 4. 📝 Update Transaction     │
                │ 5. ❌ Remove Transaction     │
                --------------------------------
                Choose an option:\s"""
        );
    }

    private void exitMenu() {
        System.out.print("""
                --------------------------------
                │  🔄 ℝ𝔼𝕋𝕌ℝℕ𝕀ℕ𝔾 𝕋𝕆 𝕄𝔸𝕀ℕ 𝕄𝔼ℕ𝕌   │
                --------------------------------
                """
        );
        exit = true;
    }

    private void invalidChoice() {
        System.out.println("Invalid choice. Please try again.");
    }
}
