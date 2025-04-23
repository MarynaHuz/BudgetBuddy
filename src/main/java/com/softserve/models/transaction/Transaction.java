package com.softserve.models.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.models.account.Currency;
import com.softserve.models.category.Category;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.softserve.validators.DateValidator.validateDate;

@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    private int accountId;
    @Setter
    private int transactionId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate transactionDate;
    private String transactionType;
    private Category category;
    private BigDecimal transactionAmount;
    private Currency currency;

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = validateDate(transactionDate);
    }
}
