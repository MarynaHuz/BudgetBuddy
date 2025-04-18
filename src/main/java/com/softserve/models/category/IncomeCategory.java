package com.softserve.models.category;

import java.math.BigDecimal;

public enum IncomeCategory implements Category {

    SALARY("Salary"),
    FREELANCE("Freelance"),
    BONUS("Bonus"),
    GIFTS("Gifts"),
    INVESTMENTS("Investments"),
    RENTAL_INCOME("Rental Income"),
    OTHER_INCOME("Other Income");

    private static final String CATEGORY_TYPE = "Income";
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
}
