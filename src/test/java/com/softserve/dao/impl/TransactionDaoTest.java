package com.softserve.dao.impl;

import com.softserve.models.account.Currency;
import com.softserve.models.transaction.Transaction;
import com.softserve.utils.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.softserve.utils.AppConfig.TRANSACTIONS_JSON;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

class TransactionDaoTest {

    private static final String FILE_PATH = TRANSACTIONS_JSON.getPath();
    private TransactionDao transactionDao;

    @BeforeEach
    void setUp() {
        transactionDao = new TransactionDao();
    }

    @Test
    void save_shouldWriteTransactionsToFile_whenInvoked() throws IOException {
        List<Transaction> transactions = List.of(
                Transaction.builder()
                        .transactionId(1)
                        .accountId(101)
                        .transactionType("EXPENSE")
                        .transactionDate(LocalDate.now())
                        .category("Food")
                        .transactionAmount(BigDecimal.valueOf(500))
                        .currency(Currency.USD)
                        .build()
        );

        try (MockedStatic<JsonUtil> mockedStatic = mockStatic(JsonUtil.class)) {
            transactionDao.save(transactions);

            mockedStatic.verify(() -> JsonUtil.writeToJson(FILE_PATH, transactions));
        }
    }

    @Test
    void save_shouldHandleEmptyList_whenNoTransactions() throws IOException {
        List<Transaction> emptyTransactions = Collections.emptyList();

        try (MockedStatic<JsonUtil> mockedStatic = mockStatic(JsonUtil.class)) {
            transactionDao.save(emptyTransactions);

            mockedStatic.verify(() -> JsonUtil.writeToJson(FILE_PATH, emptyTransactions));
        }
    }

    @Test
    void save_shouldThrowIOException_whenFileWriteFails() {
        List<Transaction> transactions = List.of(
                Transaction.builder()
                        .transactionId(1)
                        .accountId(101)
                        .transactionType("EXPENSE")
                        .transactionDate(LocalDate.now())
                        .category("Groceries")
                        .transactionAmount(BigDecimal.valueOf(500))
                        .currency(Currency.USD)
                        .build()
        );

        try (MockedStatic<JsonUtil> mockedStatic = mockStatic(JsonUtil.class)) {
            mockedStatic.when(() -> JsonUtil.writeToJson(FILE_PATH, transactions))
                    .thenThrow(new IOException("Simulated IO exception"));

            assertThrows(IOException.class, () -> transactionDao.save(transactions));
        }
    }

    @Test
    void getAll_shouldReturnAllTransactions_whenFileExists() throws IOException {
        List<Transaction> expectedTransactions = List.of(
                Transaction.builder()
                        .transactionId(1)
                        .accountId(101)
                        .transactionType("EXPENSE")
                        .transactionDate(LocalDate.now())
                        .category("Groceries")
                        .transactionAmount(BigDecimal.valueOf(500))
                        .currency(Currency.USD)
                        .build(),
                Transaction.builder()
                        .transactionId(2)
                        .accountId(102)
                        .transactionType("INCOME")
                        .transactionDate(LocalDate.now())
                        .category("Salary")
                        .transactionAmount(BigDecimal.valueOf(750))
                        .currency(Currency.EUR)
                        .build()
        );

        try (MockedStatic<JsonUtil> mockedStatic = mockStatic(JsonUtil.class)) {
            mockedStatic.when(() -> JsonUtil.readFromJson(eq(FILE_PATH), any()))
                    .thenReturn(Optional.of(expectedTransactions));

            List<Transaction> result = transactionDao.getAll();

            assertEquals(expectedTransactions, result);
            assertEquals(2, result.size());
            assertEquals(1, result.get(0).getTransactionId());
            assertEquals("Groceries", result.get(0).getCategory());
            assertEquals(2, result.get(1).getTransactionId());
            assertEquals("Salary", result.get(1).getCategory());
        }
    }

    @Test
    void getAll_shouldReturnEmptyList_whenFileDoesNotExist() throws IOException {
        try (MockedStatic<JsonUtil> mockedStatic = mockStatic(JsonUtil.class)) {
            mockedStatic.when(() -> JsonUtil.readFromJson(eq(FILE_PATH), any()))
                    .thenReturn(Optional.empty());

            List<Transaction> result = transactionDao.getAll();

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    @Test
    void getAll_shouldThrowIOException_whenReadFails() {
        try (MockedStatic<JsonUtil> mockedStatic = mockStatic(JsonUtil.class)) {
            mockedStatic.when(() -> JsonUtil.readFromJson(eq(FILE_PATH), any()))
                    .thenThrow(new IOException("Simulated read failure"));

            assertThrows(IOException.class, () -> transactionDao.getAll());
        }
    }

}