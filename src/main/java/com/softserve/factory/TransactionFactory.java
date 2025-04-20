package com.softserve.factory;

import com.softserve.models.account.Account;
import com.softserve.models.account.Currency;
import com.softserve.models.category.Category;
import com.softserve.models.transaction.Transaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.softserve.utils.AppConfig.ACCOUNTS_JSON;
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
    ) throws IOException {
        int validatedAccountId = validateId(ACCOUNTS_JSON.getPath(), accountId,
                Account::getAccountId);
        LocalDate validatedTransactionDate = validateDate(transactionDate);
        Category validatedTransactionType = getCategoryByName(transactionType);
        BigDecimal validatedTransactionAmount = validateAmount(transactionAmount,
                validatedTransactionType);

        return Transaction.builder()
                .accountId(validatedAccountId)
                .transactionId(0)
                .transactionDate(validatedTransactionDate)
                .transactionType(validatedTransactionType)
                .transactionAmount(validatedTransactionAmount)
                .currency(Currency.UAH)
                .build();
    }
}
