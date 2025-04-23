package com.softserve.models.category;

import com.softserve.models.transaction.TransactionType;

import java.math.BigDecimal;

public enum IncomeCategory implements Category {

    SALARY("Salary"),
    FREELANCE("Freelance"),
    BONUS("Bonus"),
    GIFTS("Gifts"),
    INVESTMENTS("Investments"),
    RENTAL_INCOME("Rental Income"),
    OTHER_INCOME("Other Income");

    private static final TransactionType CATEGORY_TYPE = TransactionType.INCOME;
    private final String categoryName;

    IncomeCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public boolean isValidAmount(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public TransactionType getCategoryType() {
        return CATEGORY_TYPE;
    }
}
