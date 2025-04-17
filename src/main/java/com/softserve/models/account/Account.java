package com.softserve.models.account;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@EqualsAndHashCode
public class Account {

    private int id;
    @Setter
    private String accountName;
    private Currency currency;
    @Positive
    private BigDecimal balance;
}

