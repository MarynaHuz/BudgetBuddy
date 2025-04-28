package com.softserve.models.account;

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
    private BigDecimal balance;
}
