package com.softserve.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.models.category.Category;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@EqualsAndHashCode
public class Transaction {

    private String transactionId;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate transactionDate;
    private Category category;
    private double transactionAmount;
    private String description;

    private static int idCounter = 100;

}
