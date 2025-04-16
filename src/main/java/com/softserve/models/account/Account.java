package com.softserve.models.account;

import com.softserve.models.transaction.Transaction;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.softserve.validators.BalanceValidator.validateBalance;
import static com.softserve.validators.CurrencyValidator.validateCurrency;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Account {

    private static int nextId = 1;

    private int id;
    @Setter
    private String accountName;
    private Currency currency;
    @Positive
    private BigDecimal balance;
    private List<Transaction> transactions;

    public Account(String accountName, String currency, String balance) {
        this.id = nextId++;
        this.accountName = accountName;
        this.currency = validateCurrency(currency);
        this.balance = validateBalance(balance);
        this.transactions = new ArrayList<>();
    }
}
