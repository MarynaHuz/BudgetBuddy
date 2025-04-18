package com.softserve.factory;

import com.softserve.models.account.Currency;
import com.softserve.models.category.Category;
import com.softserve.models.transaction.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.softserve.utils.IdGeneratorUtil.generateNextId;
import static com.softserve.validators.AmountValidator.validateAmount;
import static com.softserve.validators.DateValidator.validateDate;
import static com.softserve.validators.EnumValidator.validateEnum;

public class TransactionFactory {

    private TransactionFactory() {
    }

    public static <E extends Enum<E> & Category> Transaction createTransaction(int accountId,
                                                                               String transactionType,
                                                                               String transactionDate,
                                                                               String transactionAmount,
                                                                               Class<E> enumClass,
                                                                               List<Transaction> transactions) {
        //TODO: add accountId validator
        int validatedAccountId = accountId;
        LocalDate validatedTransactionDate = validateDate(transactionDate);
        Category validatedTransactionType = validateEnum(transactionType, enumClass);
        BigDecimal validatedTransactionAmount =
                validateAmount(transactionAmount, validatedTransactionType);
        Currency validatedCurrency = Currency.UAH; //TODO: add logic to get currency by accountId

        int nextId = generateNextId(transactions, Transaction::getTransactionId);

        return Transaction.builder()
                .accountId(validatedAccountId)
                .transactionId(nextId)
                .transactionDate(validatedTransactionDate)
                .transactionType(validatedTransactionType)
                .transactionAmount(validatedTransactionAmount)
                .currency(validatedCurrency)
                .build();
    }
}
