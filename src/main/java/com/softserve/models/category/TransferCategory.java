package com.softserve.models.category;

import com.softserve.models.transaction.TransactionType;

public enum TransferCategory implements Category {
    INTERNAL_TRANSFER("Internal Transfer");

    private final String categoryName;

    TransferCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public TransactionType getTransactionType() {
        return TransactionType.TRANSFER;
    }
}
