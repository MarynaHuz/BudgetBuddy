package com.softserve.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppConfig {

    ACCOUNTS_JSON("data/accounts.json"),
    TRANSACTIONS_JSON("data/transactions.json");

    private final String path;

}
