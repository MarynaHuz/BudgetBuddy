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

import static org.junit.jupiter.api.Assertions.*;
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

    @DisplayName("findById should return transaction when it exists")
    @Test
    void findById_shouldReturnTransaction_whenExists() throws IOException {
        when(transactionDao.getAll()).thenReturn(transactions);

        Optional<Transaction> result = transactionService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getTransactionId());
    }

    @DisplayName("findById should return empty optional when transaction does not exist")
    @Test
    void findById_shouldReturnEmptyOptional_whenTransactionDoesNotExist() throws IOException {
        when(transactionDao.getAll()).thenReturn(transactions);

        Optional<Transaction> result = transactionService.findById(99);

        assertTrue(result.isEmpty());
    }

    @DisplayName("listAll should return all transactions")
    @Test
    void listAll_shouldReturnAllTransactions() throws IOException {
        when(transactionDao.getAll()).thenReturn(transactions);

        List<Transaction> result = transactionService.listAll();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getTransactionId());
        assertEquals(2, result.get(1).getTransactionId());
    }

    @DisplayName("update should return empty optional as transactions are immutable")
    @Test
    void update_shouldReturnEmptyOptional_asTransactionsAreImmutable() throws IOException {
        Optional<Transaction> result = transactionService.update(expenseTransaction);

        assertTrue(result.isEmpty());
        verify(transactionDao, never()).save(any());
        verify(accountService, never()).update(any());
    }

    @DisplayName("removeById should remove expense transaction and update account balance")
    @Test
    void removeById_shouldRemoveExpenseTransaction_andUpdateAccountBalance() throws IOException {
        when(transactionDao.getAll()).thenReturn(new ArrayList<>(transactions));
        when(accountService.findById(1)).thenReturn(Optional.of(account));

        Optional<Transaction> result = transactionService.removeById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getTransactionId());
        assertEquals(new BigDecimal("1100.00"), account.getBalance());
        verify(accountService).update(account);
        verify(transactionDao).save(any());
    }

    @DisplayName("removeById should remove income transaction when account has sufficient balance")
    @Test
    void removeById_shouldRemoveIncomeTransaction_whenSufficientBalance() throws IOException {
        when(transactionDao.getAll()).thenReturn(new ArrayList<>(transactions));
        when(accountService.findById(1)).thenReturn(Optional.of(account));

        Optional<Transaction> result = transactionService.removeById(2);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().getTransactionId());
        assertEquals(new BigDecimal("800.00"), account.getBalance());
        verify(accountService).update(account);
        verify(transactionDao).save(any());
    }

    @DisplayName("removeById should throw exception when removing income transaction with insufficient balance")
    @Test
    void removeById_shouldThrowException_whenRemovingIncomeTransactionWithInsufficientBalance() throws IOException {
        when(transactionDao.getAll()).thenReturn(new ArrayList<>(transactions));
        when(accountService.findById(1)).thenReturn(Optional.of(account));

        account.setBalance(new BigDecimal("150.00"));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> transactionService.removeById(2)
        );

        assertEquals("Cannot remove income transaction: insufficient account balance",
                exception.getMessage());
        verify(transactionDao, never()).save(any());
        verify(accountService, never()).update(account);
    }

    @DisplayName("removeById should return empty optional when transaction does not exist")
    @Test
    void removeById_shouldReturnEmptyOptional_whenTransactionDoesNotExist() throws IOException {
        when(transactionDao.getAll()).thenReturn(new ArrayList<>(transactions));

        Optional<Transaction> result = transactionService.removeById(99);

        assertTrue(result.isEmpty());
        verify(transactionDao, never()).save(any());
        verify(accountService, never()).update(any());
    }

    @DisplayName("removeById should throw exception when associated account not found")
    @Test
    void removeById_shouldThrowException_whenAssociatedAccountNotFound() throws IOException {
        when(transactionDao.getAll()).thenReturn(new ArrayList<>(transactions));
        when(accountService.findById(1)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> transactionService.removeById(1)
        );

        assertEquals("Associated account not found for transaction: 1",
                exception.getMessage());
        verify(transactionDao, never()).save(any());
    }

}
