package com.softserve.ui;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.softserve.formatters.InfoFormatter.*;
import static com.softserve.models.account.Currency.getAllCurrencyCodes;

public class InfoMenu implements Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final Map<String, Runnable> actions;
    private boolean exit;

    public InfoMenu() {
        this.actions = Map.of(
                "0", this::exitMenu,
                "1", this::displayAllCurrencies,
                "2", this::displayExpenseCategories,
                "3", this::displayIncomeCategories,
                "4", this::displayAllCategories
        );
    }

    @Override
    public void show() {
        exit = false;
        while (!exit) {
            displayMenuOptions();
            String action = scanner.nextLine();
            actions.getOrDefault(action, this::invalidChoice).run();
        }
    }

    private static void displayMenuOptions() {
        System.out.print("""
                
                ------------------------------------
                │      📊 𝕊𝕐𝕊𝕋𝔼𝕄 𝕀ℕ𝔽𝕆ℝ𝕄𝔸𝕋𝕀𝕆ℕ       │
                ------------------------------------
                │ 0. 🔙 Back to Main Menu          │
                │ 1. 💱 View All Currencies        │
                │ 2. 📉 View Expense Categories    │
                │ 3. 📈 View Income Categories     │
                │ 4. 📊 View All Categories        │
                ------------------------------------
                Choose an option:\s"""
        );
    }

    private void displayAllCurrencies() {
        List<String> allCurrencies = getAllCurrencyCodes();
        System.out.println(formatCurrencies(allCurrencies));
    }

    private void displayExpenseCategories() {
        System.out.println(formatExpenseCategories());
    }

    private void displayIncomeCategories() {
        System.out.println(formatIncomeCategories());
    }

    private void displayAllCategories() {
        System.out.println(formatAllCategories());
    }

    private void exitMenu() {
        System.out.print("""
                ------------------------------------
                │    🔄 ℝ𝔼𝕋𝕌ℝℕ𝕀ℕ𝔾 𝕋𝕆 𝕄𝔸𝕀ℕ 𝕄𝔼ℕ𝕌     │
                ------------------------------------"""
        );
        exit = true;
    }

    private void invalidChoice() {
        System.out.print("""
                ------------------------------------
                │          ⚠️  WARNING             │
                ------------------------------------
                │ ❌ Invalid choice.               │
                │ 🔄 Please try again.             │
                │ ⌨️ Enter a number (0-4).         │
                ------------------------------------
                """
        );
    }
}
