package com.softserve.ui;

import com.softserve.controllers.AccountController;

import java.util.List;
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

        List<String> prompts = List.of(
                "Enter account name: ",
                "Choose currency (e.g., UAH, USD, EUR): ",
                "Enter initial balance: "
        );

        List<String> accountDetails = prompts.stream()
                .map(prompt -> {
                            System.out.println(prompt);
                            return scanner.nextLine().trim();
                        }
                ).toList();

        accountController.create(accountDetails);
    }

    private void findAccountById() {
        System.out.print("Enter the transaction ID: ");
        String id = scanner.nextLine();
        accountController.findById(id);
    }

    private void viewAccounts() {
        accountController.listAll();
    }

    private void updateAccount() {

        System.out.println("Enter Account ID to Update: ");
        String id = scanner.nextLine();

        List<String> prompts = List.of(
                "Enter account name: ",
                "Choose currency (e.g., UAH, USD, EUR): ",
                "Enter initial balance: "
        );

        List<String> accountDetails = prompts.stream()
                .map(prompt -> {
                            System.out.println(prompt);
                            return scanner.nextLine().trim();
                        }
                ).toList();

        Map<String, List<String>> accountParameters = Map.of(id, accountDetails);
        accountController.update(accountParameters);
    }

    private void removeAccount() {

    }

    private void makeInternalTransfer() {

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
