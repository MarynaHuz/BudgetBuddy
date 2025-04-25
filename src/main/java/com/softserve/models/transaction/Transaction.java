package com.softserve.models.transaction;

import com.softserve.models.account.Currency;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Setter(AccessLevel.NONE)
    private int transactionId;
    private int accountId;
    private String transactionType;
    private LocalDate transactionDate;
    private String category;
    private BigDecimal transactionAmount;
    private Currency currency;

}
