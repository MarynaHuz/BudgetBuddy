package com.softserve.models.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.models.account.Currency;
import com.softserve.models.category.Category;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    private int transactionId;
    private int accountId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate transactionDate;
    private Category transactionType;
    private BigDecimal transactionAmount;
    private Currency currency;
}
