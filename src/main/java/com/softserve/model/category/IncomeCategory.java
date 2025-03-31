package com.softserve.model.category;

public enum IncomeCategory implements Category {

    SALARY("Salary", CategoryType.INCOME),
    FREELANCE("Freelance", CategoryType.INCOME),
    BONUS("Bonus", CategoryType.INCOME),
    GIFTS("Gifts", CategoryType.INCOME),
    INVESTMENTS("Investments", CategoryType.INCOME),
    RENTAL_INCOME("Rental Income", CategoryType.INCOME),
    OTHER_INCOME("Other Income", CategoryType.INCOME);

    private final String categoryName;
    private final CategoryType categoryType;

    IncomeCategory(String categoryName, CategoryType categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    @Override
    public String getCategoryName() {
        return categoryName;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }
}
