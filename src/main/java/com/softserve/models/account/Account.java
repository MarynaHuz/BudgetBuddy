package com.softserve.models.account;

import com.softserve.models.Transaction;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.softserve.validators.AccountValidation.validateCurrency;
import static com.softserve.validators.AccountValidation.validateInitialBalance;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Account {

    private static int nextId = 1;

    private long id;
    @Setter
    private String accountName;
    private Currency currency;
    private BigDecimal initialBalance;
    private List<Transaction> transactions;

    public Account(String accountName, String currency, String initialBalance) {
        this.id = nextId++;
        this.accountName = accountName;
        this.currency = validateCurrency(currency);
        this.initialBalance = validateInitialBalance(initialBalance);
        this.transactions = new ArrayList<>();
    }
}
