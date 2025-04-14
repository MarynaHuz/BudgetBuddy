package com.softserve.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.models.category.Category;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.softserve.validators.AmountValidator.validateAmount;

@NoArgsConstructor
@EqualsAndHashCode
public class Transaction {

    private long transactionId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate transactionDate;
    private Category transactionType;
    private BigDecimal transactionAmount;
    private String description;

    private static int nextId = 100;

    public Transaction(LocalDate transactionDate,
                       Category transactionType,
                       String transactionAmount,
                       String description) {

        this.transactionId = nextId++;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.transactionAmount = validateAmount(transactionAmount,
                transactionType.getCategoryType());
        this.description =
                description != null ? description : transactionType.getCategoryName();

    }
}
