package com.softserve.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.model.category.Category;

import java.time.LocalDate;

public class Transaction {

    private int transactionId;

    private Category category;
    private double transactionAmount;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate transactionDate;


    private static int idCounter = 1;


}
