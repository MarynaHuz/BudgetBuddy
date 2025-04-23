package com.softserve.factory;

import com.softserve.models.account.Currency;
import com.softserve.models.category.Category;
import com.softserve.models.transaction.Transaction;
import com.softserve.models.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.softserve.utils.CategoryManager.getCategoryByName;
import static com.softserve.validators.AmountValidator.validateAmount;
import static com.softserve.validators.DateValidator.validateDate;
import static com.softserve.validators.IdValidator.validateId;

public class TransactionFactory {

    private TransactionFactory() {
    }

    public static Transaction createTransaction(String accountId,
                                                String transactionType,
                                                String transactionDate,
                                                String transactionAmount
    ) {
        int validAccountId = validateId(accountId);
        LocalDate validTransactionDate = validateDate(transactionDate);
        Category categoryByName = getCategoryByName(transactionType);
        TransactionType type = categoryByName.getTransactionType();
        BigDecimal validatedTransactionAmount = validateAmount(transactionAmount);

        return Transaction.builder()
                .accountId(validAccountId)
                .transactionId(0)
                .transactionDate(validTransactionDate)
                .transactionType(type)
                .category(categoryByName)
                .transactionAmount(validatedTransactionAmount)
                .currency(Currency.UAH)
                .build();
    }
}
