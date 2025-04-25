package com.softserve.models.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private int transactionId;
    private int accountId;
    private String transactionType;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate transactionDate;
    private String category;
    private BigDecimal transactionAmount;
    private Currency currency;

}
