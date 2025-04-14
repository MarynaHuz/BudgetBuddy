package com.softserve.models.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum CategoryType {

    INCOME("Income"),
    EXPENSE("Expense");

    private final String categoryTypeName;

    public static boolean isValidAmountForCategory(BigDecimal amount,
                                                   CategoryType categoryType) {
        if (categoryType.equals(INCOME)) {
            return amount.compareTo(BigDecimal.ZERO) > 0;
        } else if (categoryType.equals(EXPENSE)) {
            return amount.compareTo(BigDecimal.ZERO) <= 0;
        }
        return false;
    }
}
