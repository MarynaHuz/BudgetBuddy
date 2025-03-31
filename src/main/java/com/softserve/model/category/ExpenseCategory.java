package com.softserve.model.category;

public enum ExpenseCategory implements Category {

    FOOD("Food"),
    DINING_OUT("Dining Out"),
    TRANSPORTATION("Transportation"),
    TAXI("Taxi"),
    RENT("Rent"),
    UTILITIES("Utilities"),
    ENTERTAINMENT("Entertainment"),
    GAMES("Games"),
    HEALTHCARE("Healthcare"),
    EDUCATION("Education"),
    BOOKS("Books"),
    SHOPPING("Shopping"),
    TRAVEL("Travel"),
    MISCELLANEOUS("Miscellaneous"),
    SUBSCRIPTIONS("Subscriptions"),
    GIFTING("Gifting"),
    HOME_IMPROVEMENT("Home Improvement"),
    PERSONAL_CARE("Personal Care"),
    TAXES("Taxes"),
    CHARITY("Charity"),
    HOBBY("Hobby");

    private final String categoryName;

    ExpenseCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String getCategoryName() {
        return categoryName;
    }
}
