package com.softserve.dao;

import com.softserve.models.account.Account;

import java.util.List;

public interface AccountDAO {

    void saveAccount(Account account);

    Account findByName(String accountName);

    boolean existsByName(String accountName);

    List<Account> readAccounts();
}
