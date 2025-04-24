package com.softserve.factory;

import com.softserve.models.account.Currency;
import com.softserve.models.category.Category;
import com.softserve.models.transaction.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.softserve.utils.CategoryManager.getCategoryByName;

public class TransactionFactory {

    private TransactionFactory() {
    }

    public static Transaction createTransaction(int accountId,
                                                String transactionCategory,
                                                LocalDate transactionDate,
                                                BigDecimal transactionAmount
    ) {
        Category categoryByName = getCategoryByName(transactionCategory);
        String categoryName = categoryByName.getCategoryName();
        String type = categoryByName.getTransactionType().getTypeName();

        return Transaction.builder()
                .transactionId(0)
                .accountId(accountId)
                .transactionType(type)
                .transactionDate(transactionDate)
                .category(categoryName)
                .transactionAmount(transactionAmount)
                .currency(Currency.UAH)
                .build();
    }
}
