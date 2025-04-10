package com.softserve.dao;

import com.softserve.models.account.Account;

import java.util.List;
import java.util.Optional;

public interface AccountDAO {

    void saveAccount(Account account);

    Optional<Account> findByName(String accountName);

    boolean existsByName(String accountName);

    List<Account> readAccounts();
}
