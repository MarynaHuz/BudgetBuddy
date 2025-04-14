package com.softserve.models.category;

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

}
