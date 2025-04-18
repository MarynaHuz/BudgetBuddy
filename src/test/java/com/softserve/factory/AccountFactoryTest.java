package com.softserve.factory;

import com.softserve.models.account.Account;
import com.softserve.models.account.Currency;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountFactoryTest {

    @Test
    void createAccount_ReturnsAccount_IfInputsAreValid() {
        String accountName = "ValidAccount";
        String currency = "USD";
        String balance = "1000.50";
        List<Account> accounts = new ArrayList<>();

        Account result = AccountFactory.createAccount(accountName, currency, balance, accounts);

        assertNotNull(result);
        assertEquals(1, result.getAccountId());
        assertEquals("ValidAccount", result.getAccountName());
        assertEquals(Currency.USD, result.getCurrency());
        assertEquals(new BigDecimal("1000.50"), result.getBalance());
    }

    @Test
    void createAccount_ThrowsException_IfAccountNameIsInvalid() {
        String invalidAccountName = "";
        String currency = "USD";
        String balance = "1000.50";
        List<Account> accounts = new ArrayList<>();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                AccountFactory.createAccount(invalidAccountName, currency, balance, accounts));

        assertEquals("Invalid account name: ", exception.getMessage());
    }

    @Test
    void createAccount_ThrowsException_IfBalanceIsInvalid() {
        String accountName = "ValidAccount";
        String currency = "USD";
        String invalidBalance = "-500";
        List<Account> accounts = new ArrayList<>();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                AccountFactory.createAccount(accountName, currency, invalidBalance, accounts));

        assertEquals("Balance must be a positive value", exception.getMessage());
    }

    @Test
    void createAccount_ThrowsException_IfCurrencyIsInvalid() {
        String accountName = "ValidAccount";
        String invalidCurrency = "INVALID";
        String balance = "1000.00";
        List<Account> accounts = new ArrayList<>();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                AccountFactory.createAccount(accountName, invalidCurrency, balance, accounts));

        assertEquals("Invalid Currency supplied: INVALID", exception.getMessage());
    }

    @Test
    void createAccount_ReturnsAccountWithCorrectId_IfAccountsExist() {
        String accountName = "NewAccount";
        String currency = "EUR";
        String balance = "200.00";
        List<Account> accounts = List.of(
                Account.builder().accountId(1).accountName("ExistingAccount1")
                        .currency(Currency.USD).balance(BigDecimal.TEN).build(),
                Account.builder().accountId(2).accountName("ExistingAccount2")
                        .currency(Currency.EUR).balance(BigDecimal.ONE).build()
        );
        Account result = AccountFactory.createAccount(accountName, currency, balance, accounts);

        assertNotNull(result);
        assertEquals(3, result.getAccountId());
        assertEquals("NewAccount", result.getAccountName());
        assertEquals(Currency.EUR, result.getCurrency());
        assertEquals(new BigDecimal("200.00"), result.getBalance());
    }
}