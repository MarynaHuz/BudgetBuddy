package com.softserve.models.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.models.account.Currency;
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
    private String category;
    private BigDecimal transactionAmount;
    @Setter
    private Currency currency;

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = validateDate(transactionDate);
    }
}
