package com.softserve.formatters;

import com.softserve.models.category.ExpenseCategory;
import com.softserve.models.category.IncomeCategory;

import java.util.List;

public class InfoFormatter {

    private InfoFormatter() {
    }

    public static String formatCurrencies(List<String> currencies) {
        if (currencies == null || currencies.isEmpty()) {
            return "No currencies available.";
        }

        String currenciesLine = String.join(" │ ", currencies);

        StringBuilder result = new StringBuilder();
        result.append("""
                ------------------------------
                │💱 𝔸𝕍𝔸𝕀𝕃𝔸𝔹𝕃𝔼 ℂ𝕌ℝℝ𝔼ℕℂ𝕀𝔼𝕊     │
                ------------------------------
                │\s""");
        result.append(currenciesLine);
        result.append("\n");
        result.append("-".repeat(30));
        return result.toString();
    }


    public static String formatExpenseCategories() {
        ExpenseCategory[] categories = ExpenseCategory.values();

        StringBuilder result = new StringBuilder();
        result.append("""
                -------------------------------
                │    📉 𝔼𝕏ℙ𝔼ℕ𝕊𝔼 ℂ𝔸𝕋𝔼𝔾𝕆ℝ𝕀𝔼𝕊    │
                -------------------------------
                """);

        for (ExpenseCategory category : categories) {
            result.append(String.format("│ %s %s%n", getCategoryEmoji(category), category.getCategoryName()));
        }

        result.append("-".repeat(30));
        return result.toString();
    }

    public static String formatIncomeCategories() {
        IncomeCategory[] categories = IncomeCategory.values();

        StringBuilder result = new StringBuilder();
        result.append("""
                ------------------------------
                │    📈 𝕀ℕℂ𝕆𝕄𝔼 ℂ𝔸𝕋𝔼𝔾𝕆ℝ𝕀𝔼𝕊    │
                ------------------------------
                """);

        for (IncomeCategory category : categories) {
            result.append(String.format("│ %s %s%n", getIncomeCategoryEmoji(category), category.getCategoryName()));
        }

        result.append("-".repeat(30));
        return result.toString();
    }

    public static String formatAllCategories() {
        ExpenseCategory[] expenseCategories = ExpenseCategory.values();
        IncomeCategory[] incomeCategories = IncomeCategory.values();

        StringBuilder result = new StringBuilder();
        result.append("""
                ------------------------------
                │   📊 𝔸𝕃𝕃 ℂ𝔸𝕋𝔼𝔾𝕆ℝ𝕀𝔼𝕊        │
                ------------------------------
                │ 📉 𝔼𝕏ℙ𝔼ℕ𝕊𝔼 ℂ𝔸𝕋𝔼𝔾𝕆ℝ𝕀𝔼𝕊:
                """);

        for (ExpenseCategory category : expenseCategories) {
            result.append(String.format("│ %s %s%n", getCategoryEmoji(category), category.getCategoryName()));
        }

        result.append("│\n│ 📈 𝕀ℕℂ𝕆𝕄𝔼 ℂ𝔸𝕋𝔼𝔾𝕆ℝ𝕀𝔼𝕊:\n");

        for (IncomeCategory category : incomeCategories) {
            result.append(String.format("│ %s %s%n", getIncomeCategoryEmoji(category), category.getCategoryName()));
        }

        result.append("-".repeat(30));
        return result.toString();
    }

    private static String getCategoryEmoji(ExpenseCategory category) {
        return switch (category) {
            case FOOD -> "🍎";
            case DINING_OUT -> "🍽️";
            case TRANSPORTATION -> "🚌";
            case TAXI -> "🚕";
            case RENT -> "🏠";
            case UTILITIES -> "💡";
            case ENTERTAINMENT -> "🎭";
            case GAMES -> "🎮";
            case HEALTHCARE -> "⚕️";
            case EDUCATION -> "🎓";
            case BOOKS -> "📚";
            case SHOPPING -> "🛒";
            case TRAVEL -> "✈️";
            case MISCELLANEOUS -> "🔄";
            case SUBSCRIPTIONS -> "📱";
            case GIFTING -> "🎁";
            case HOME_IMPROVEMENT -> "🔨";
            case PERSONAL_CARE -> "💅";
            case TAXES -> "💰";
            case CHARITY -> "❤️";
            case HOBBY -> "🎨";
        };
    }

    private static String getIncomeCategoryEmoji(IncomeCategory category) {
        return switch (category) {
            case SALARY -> "💼";
            case FREELANCE -> "💻";
            case BUSINESS -> "🏢";
            case RENTAL -> "🏘️";
            case BONUS -> "🎯";
            case GIFTS -> "🎁";
            case INVESTMENTS -> "📊";
            case OTHER -> "🔄";
        };
    }

}
