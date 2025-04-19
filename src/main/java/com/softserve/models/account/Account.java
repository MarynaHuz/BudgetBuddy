package com.softserve.models.account;

import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

import static com.softserve.validators.AccountNameValidator.validateAccountName;


@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Setter
    private int accountId;
    private String accountName;
    private Currency currency;
    @Positive
    private BigDecimal balance;

    public void setAccountName(String accountName) {
        this.accountName = validateAccountName(accountName);
    }

    public void setCurrency(Currency currency) {
        //TODO: check if any transactions were added to this account.
        // If they were added you can't change the currency due to problems with exchange rate
        this.currency = currency;
    }
}
