package com.softserve.models.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.models.account.Currency;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.softserve.models.transaction.TransactionType.EXPENSE;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    private int transactionId;
    private int accountId;
    private String transactionType;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate transactionDate;
    private String category;
    private BigDecimal transactionAmount;
    private Currency currency;

    public boolean isExpense() {
        return EXPENSE.getTypeName().equalsIgnoreCase(transactionType);
    }
}
