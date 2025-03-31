package com.softserve.model.category;

public enum ExpenseCategory implements Category {

    FOOD("Food", CategoryType.EXPENSE),
    DINING_OUT("Dining Out", CategoryType.EXPENSE),
    TRANSPORTATION("Transportation", CategoryType.EXPENSE),
    TAXI("Taxi", CategoryType.EXPENSE),
    RENT("Rent", CategoryType.EXPENSE),
    UTILITIES("Utilities", CategoryType.EXPENSE),
    ENTERTAINMENT("Entertainment", CategoryType.EXPENSE),
    GAMES("Games", CategoryType.EXPENSE),
    HEALTHCARE("Healthcare", CategoryType.EXPENSE),
    EDUCATION("Education", CategoryType.EXPENSE),
    BOOKS("Books", CategoryType.EXPENSE),
    SHOPPING("Shopping", CategoryType.EXPENSE),
    TRAVEL("Travel", CategoryType.EXPENSE),
    MISCELLANEOUS("Miscellaneous", CategoryType.EXPENSE),
    SUBSCRIPTIONS("Subscriptions", CategoryType.EXPENSE),
    GIFTING("Gifting", CategoryType.EXPENSE),
    HOME_IMPROVEMENT("Home Improvement", CategoryType.EXPENSE),
    PERSONAL_CARE("Personal Care", CategoryType.EXPENSE),
    TAXES("Taxes", CategoryType.EXPENSE),
    CHARITY("Charity", CategoryType.EXPENSE),
    HOBBY("Hobby", CategoryType.EXPENSE);

    private final String categoryName;
    private final CategoryType categoryType;

    ExpenseCategory(String categoryName, CategoryType categoryType) {
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
