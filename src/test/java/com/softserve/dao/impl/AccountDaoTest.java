package com.softserve.dao.impl;

import com.softserve.models.account.Account;
import com.softserve.models.account.Currency;
import com.softserve.utils.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.softserve.utils.AppConfig.ACCOUNTS_JSON;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

class AccountDaoTest {

    private static final String FILE_PATH = ACCOUNTS_JSON.getPath();
    private AccountDao accountDao;

    @BeforeEach
    void setUp() {
        accountDao = new AccountDao();
    }

    @Test
    void save_shouldWriteAccountsToFile_whenInvoked() throws IOException {
        List<Account> accounts = List.of(
                Account.builder()
                        .accountId(1)
                        .accountName("Test")
                        .currency(Currency.USD)
                        .balance(BigDecimal.valueOf(100))
                        .build()
        );

        try (MockedStatic<JsonUtil> mockedStatic = mockStatic(JsonUtil.class)) {
            accountDao.save(accounts);

            mockedStatic.verify(() -> JsonUtil.writeToJson(FILE_PATH, accounts));
        }
    }

    @Test
    void save_shouldHandleEmptyList_whenNoAccounts() throws IOException {
        List<Account> emptyAccounts = Collections.emptyList();

        try (MockedStatic<JsonUtil> mockedStatic = mockStatic(JsonUtil.class)) {
            accountDao.save(emptyAccounts);

            mockedStatic.verify(() -> JsonUtil.writeToJson(FILE_PATH, emptyAccounts));
        }
    }

    @Test
    void save_shouldThrowIOException_whenFileWriteFails() {
        List<Account> accounts = List.of(
                Account.builder()
                        .accountId(1)
                        .accountName("Test")
                        .currency(Currency.USD)
                        .balance(BigDecimal.valueOf(100))
                        .build()
        );

        try (MockedStatic<JsonUtil> mockedStatic = mockStatic(JsonUtil.class)) {
            mockedStatic.when(() -> JsonUtil.writeToJson(FILE_PATH, accounts))
                    .thenThrow(new IOException("Simulated IO exception"));

            assertThrows(IOException.class, () -> accountDao.save(accounts));
        }
    }

    @Test
    void getAll_shouldReturnAllAccounts_whenFileExists() throws IOException {
        List<Account> expectedAccounts = List.of(
                Account.builder()
                        .accountId(1)
                        .accountName("Account1")
                        .currency(Currency.USD)
                        .balance(BigDecimal.valueOf(100))
                        .build(),
                Account.builder()
                        .accountId(2)
                        .accountName("Account2")
                        .currency(Currency.EUR)
                        .balance(BigDecimal.valueOf(200))
                        .build()
        );

        try (MockedStatic<JsonUtil> mockedStatic = mockStatic(JsonUtil.class)) {
            mockedStatic.when(() -> JsonUtil.readFromJson(eq(FILE_PATH), any()))
                    .thenReturn(Optional.of(expectedAccounts));

            List<Account> result = accountDao.getAll();

            assertEquals(expectedAccounts, result);
            assertEquals(2, result.size());
            assertEquals("Account1", result.get(0).getAccountName());
            assertEquals("Account2", result.get(1).getAccountName());
        }
    }

    @Test
    void getAll_shouldReturnEmptyList_whenFileDoesNotExist() throws IOException {
        try (MockedStatic<JsonUtil> mockedStatic = mockStatic(JsonUtil.class)) {
            mockedStatic.when(() -> JsonUtil.readFromJson(eq(FILE_PATH), any()))
                    .thenReturn(Optional.empty());

            List<Account> result = accountDao.getAll();

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    @Test
    void getAll_shouldThrowIOException_whenReadFails() {
        try (MockedStatic<JsonUtil> mockedStatic = mockStatic(JsonUtil.class)) {
            mockedStatic.when(() -> JsonUtil.readFromJson(eq(FILE_PATH), any()))
                    .thenThrow(new IOException("Simulated read failure"));

            assertThrows(IOException.class, () -> accountDao.getAll());
        }
    }
}