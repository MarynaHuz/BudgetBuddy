package com.softserve.models.category;

import java.math.BigDecimal;

public enum CategoryType {

    INCOME("Income"),
    EXPENSE("Expense");

    private final String categoryTypeName;

    CategoryType(String categoryTypeName) {
        this.categoryTypeName = categoryTypeName;
    }

    public String getCategoryTypeName() {
        return categoryTypeName;
    }

    public static boolean isIncomeCategory(String categoryType) {
        return INCOME.getCategoryTypeName().equalsIgnoreCase(categoryType);
    }

    public static boolean isExpenseCategory(String categoryType) {
        return EXPENSE.getCategoryTypeName().equalsIgnoreCase(categoryType);
    }

    public static boolean isValidAmountForCategory(BigDecimal amount,
                                                   String categoryType) {
        if (isIncomeCategory(categoryType)) {
            return amount.compareTo(BigDecimal.ZERO) > 0;
        } else if (isExpenseCategory(categoryType)) {
            return amount.compareTo(BigDecimal.ZERO) <= 0;
        }
        return false;
    }
}
