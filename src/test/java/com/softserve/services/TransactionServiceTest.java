package com.softserve.services;

import com.softserve.dao.Dao;
import com.softserve.models.account.Account;
import com.softserve.models.account.Currency;
import com.softserve.models.transaction.Transaction;
import com.softserve.models.transaction.TransactionType;
import com.softserve.utils.IdManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private Dao<Transaction> transactionDao;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction expenseTransaction;
    private Transaction incomeTransaction;
    private Account account;
    private List<Transaction> transactions;
    private LocalDate transactionDate;

    @BeforeEach
    void setUp() {
        transactionDate = LocalDate.of(2023, 10, 15);

        account = Account.builder()
                .accountId(1)
                .accountName("Test Account")
                .currency(Currency.USD)
                .balance(new BigDecimal("1000.00"))
                .build();

        expenseTransaction = Transaction.builder()
                .transactionId(1)
                .accountId(1)
                .transactionType(TransactionType.EXPENSE.getTypeName())
                .transactionDate(transactionDate)
                .category("Food")
                .transactionAmount(new BigDecimal("100.00"))
                .currency(Currency.USD)
                .build();

        incomeTransaction = Transaction.builder()
                .transactionId(2)
                .accountId(1)
                .transactionType(TransactionType.INCOME.getTypeName())
                .transactionDate(transactionDate)
                .category("Salary")
                .transactionAmount(new BigDecimal("200.00"))
                .currency(Currency.USD)
                .build();

        transactions = new ArrayList<>();
        transactions.add(expenseTransaction);
        transactions.add(incomeTransaction);
    }

    @DisplayName("create should successfully create an expense transaction when account has sufficient balance")
    @Test
    void create_shouldCreateExpenseTransaction_whenSufficientBalance() throws IOException {
        when(accountService.findById(1)).thenReturn(Optional.of(account));
        when(accountService.hasEnoughBalance(eq(account), any(BigDecimal.class))).thenReturn(true);
        when(transactionDao.getAll()).thenReturn(new ArrayList<>());

        try (MockedStatic<IdManager> mockedIdManager = mockStatic(IdManager.class)) {
            mockedIdManager.when(() -> IdManager.generateNextId(anyString())).thenReturn(1);

            Transaction result = transactionService.create(expenseTransaction);

            assertEquals(1, result.getTransactionId());
            assertEquals(Currency.USD, result.getCurrency());
            verify(accountService).update(account);
            verify(transactionDao).save(any());
            assertEquals(new BigDecimal("900.00"), account.getBalance());
        }
    }

    @DisplayName("create should successfully create an income transaction")
    @Test
    void create_shouldCreateIncomeTransaction() throws IOException {
        when(accountService.findById(1)).thenReturn(Optional.of(account));
        when(transactionDao.getAll()).thenReturn(new ArrayList<>());

        try (MockedStatic<IdManager> mockedIdManager = mockStatic(IdManager.class)) {
            mockedIdManager.when(() -> IdManager.generateNextId(anyString())).thenReturn(2);

            Transaction result = transactionService.create(incomeTransaction);

            assertEquals(2, result.getTransactionId());
            assertEquals(Currency.USD, result.getCurrency());
            verify(accountService).update(account);
            verify(transactionDao).save(any());
            assertEquals(new BigDecimal("1200.00"), account.getBalance());
        }
    }

    @DisplayName("create should throw exception when account has insufficient balance")
    @Test
    void create_shouldThrowException_whenInsufficientBalance() throws IOException {
        when(accountService.findById(1)).thenReturn(Optional.of(account));
        when(accountService.hasEnoughBalance(eq(account), any(BigDecimal.class))).thenReturn(false);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> transactionService.create(expenseTransaction)
        );

        assertEquals("Insufficient balance for this transaction", exception.getMessage());
        verify(accountService, never()).update(any());
        verify(transactionDao, never()).save(any());
    }

}
