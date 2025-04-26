package com.softserve.models.account;

import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    private int accountId;
    private String accountName;
    private Currency currency;
    @Positive
    private BigDecimal balance;

}
