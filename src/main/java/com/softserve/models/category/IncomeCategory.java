package com.softserve.models.category;

public enum IncomeCategory implements Category {

    SALARY("Salary"),
    FREELANCE("Freelance"),
    BONUS("Bonus"),
    GIFTS("Gifts"),
    INVESTMENTS("Investments"),
    RENTAL_INCOME("Rental Income"),
    OTHER_INCOME("Other Income");

    private static final CategoryType CATEGORY_TYPE = CategoryType.INCOME;
    private final String categoryName;

    IncomeCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public CategoryType getCategoryType() {
        return CATEGORY_TYPE;
    }
}
