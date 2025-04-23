package com.softserve.models.category;

import com.softserve.models.transaction.TransactionType;

public enum IncomeCategory implements Category {

    SALARY("Salary"),
    FREELANCE("Freelance"),
    BONUS("Bonus"),
    GIFTS("Gifts"),
    INVESTMENTS("Investments"),
    RENTAL_INCOME("Rental Income"),
    OTHER_INCOME("Other Income");

    private final String categoryName;

    IncomeCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public TransactionType getCategoryType() {
        return TransactionType.INCOME;
    }
}
