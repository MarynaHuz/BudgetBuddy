package com.softserve.models.account;

import com.softserve.models.Transaction;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.softserve.validators.AccountValidation.validateCurrency;
import static com.softserve.validators.BalanceValidator.validateBalance;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
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
        this.initialBalance = validateBalance(initialBalance);
        this.transactions = new ArrayList<>();
    }
}
