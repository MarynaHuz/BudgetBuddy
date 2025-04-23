package com.softserve.factory;

import com.softserve.models.account.Currency;
import com.softserve.models.category.Category;
import com.softserve.models.transaction.Transaction;

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
        Category categoryByName = getCategoryByName(transactionType);

        int validAccountId = validateId(accountId);
        LocalDate validTransactionDate = validateDate(transactionDate);
        String categoryName = categoryByName.getCategoryName();
        String type = categoryByName.getTransactionType().getTypeName();
        BigDecimal validatedTransactionAmount = validateAmount(transactionAmount);

        return Transaction.builder()
                .accountId(validAccountId)
                .transactionId(0)
                .transactionDate(validTransactionDate)
                .transactionType(type)
                .category(categoryName)
                .transactionAmount(validatedTransactionAmount)
                .currency(Currency.UAH)
                .build();
    }
}
