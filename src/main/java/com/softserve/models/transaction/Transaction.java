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

    @Setter
    private int transactionId;
    private int accountId;
    private String transactionType;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate transactionDate;
    private String category;
    private BigDecimal transactionAmount;
    @Setter
    private Currency currency;

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = validateDate(transactionDate);
    }
}
