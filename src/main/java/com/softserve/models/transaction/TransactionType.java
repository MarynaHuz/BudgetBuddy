package com.softserve.models.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {

    INCOME("Income"),
    EXPENSE("Expense"),
    TRANSFER("Transfer");

    private final String typeName;
}
