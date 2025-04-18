package com.softserve.models.account;

import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;


@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    private int accountId;
    @Setter
    private String accountName;
    private Currency currency;
    @Positive
    private BigDecimal balance;
}
