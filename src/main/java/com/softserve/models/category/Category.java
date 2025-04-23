package com.softserve.models.category;

import com.softserve.models.transaction.TransactionType;

import java.math.BigDecimal;

public interface Category {

    String getCategoryName();
    boolean isValidAmount(BigDecimal amount);
    TransactionType getCategoryType();
}
