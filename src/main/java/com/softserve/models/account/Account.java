package com.softserve.models.account;

import com.softserve.models.transaction.Transaction;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
public class Account {

    private int id;
    @Setter
    private String accountName;
    private Currency currency;
    @Positive
    private BigDecimal balance;
    @Builder.Default
    private List<Transaction> transactions = new ArrayList<>();
}

