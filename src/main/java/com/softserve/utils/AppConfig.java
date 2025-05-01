package com.softserve.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppConfig {

    ACCOUNTS_JSON("data/accounts.json"),
    TRANSACTIONS_JSON("data/transactions.json"),
    ACCOUNT_ID("data/account_id.json"),
    TRANSACTION_ID("data/transaction_id.json");

    private final String path;

}
