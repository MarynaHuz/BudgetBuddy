package com.softserve.services;

import com.softserve.dao.impl.AccountDao;
import com.softserve.models.account.Account;
import com.softserve.models.account.Currency;
import com.softserve.utils.AppConfig;
import com.softserve.utils.IdManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.softserve.utils.IdManager.generateNextId;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountDao accountDao;

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(accountDao);
    }

    @Test
    void create_shouldAssignGeneratedId_whenAccountNameIsUnique() throws IOException {
        Account newAccount = Account.builder()
                .accountName("Savings")
                .currency(Currency.USD)
                .balance(BigDecimal.valueOf(1000.00))
                .build();

        List<Account> existingAccounts = new ArrayList<>();
        when(accountDao.getAll()).thenReturn(existingAccounts);

        try (MockedStatic<IdManager> mockedIdManager = mockStatic(IdManager.class)) {
            mockedIdManager.when(() -> generateNextId(AppConfig.ACCOUNT_ID.getPath())).thenReturn(1);

            Account result = accountService.create(newAccount);

            assertEquals(1, result.getAccountId());
            assertEquals("Savings", result.getAccountName());
            assertEquals(Currency.USD, result.getCurrency());
            assertEquals(BigDecimal.valueOf(1000.00), result.getBalance());

            ArgumentCaptor<List<Account>> listCaptor = ArgumentCaptor.forClass(List.class);
            verify(accountDao).save(listCaptor.capture());

            List<Account> savedAccounts = listCaptor.getValue();
            assertEquals(1, savedAccounts.size());
            assertEquals(1, savedAccounts.get(0).getAccountId());
            assertEquals("Savings", savedAccounts.get(0).getAccountName());
        }
    }

    @Test
    void create_shouldThrowIllegalArgumentException_whenAccountNameAlreadyExists() throws IOException {
        Account existingAccount = Account.builder()
                .accountId(1)
                .accountName("Savings")
                .currency(Currency.USD)
                .balance(BigDecimal.valueOf(1000.00))
                .build();

        Account newAccount = Account.builder()
                .accountName("savings")
                .currency(Currency.EUR)
                .balance(BigDecimal.valueOf(500.00))
                .build();

        List<Account> existingAccounts = new ArrayList<>();
        existingAccounts.add(existingAccount);

        when(accountDao.getAll()).thenReturn(existingAccounts);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> accountService.create(newAccount)
        );

        assertEquals("Account name already exists: savings", exception.getMessage());
        verify(accountDao, never()).save(any());
    }

    @Test
    void create_shouldPropagateIOException_whenDaoThrowsIOException() throws IOException {
        Account newAccount = Account.builder()
                .accountName("Savings")
                .currency(Currency.USD)
                .balance(BigDecimal.valueOf(1000.00))
                .build();

        when(accountDao.getAll()).thenThrow(new IOException("File not found"));

        assertThrows(IOException.class, () -> accountService.create(newAccount));
    }

    @Test
    void create_shouldAddAccountToExistingList_whenAccountNameIsUnique() throws IOException {
        Account existingAccount = Account.builder()
                .accountId(1)
                .accountName("Checking")
                .currency(Currency.USD)
                .balance(BigDecimal.valueOf(1000.00))
                .build();

        Account newAccount = Account.builder()
                .accountName("Savings")
                .currency(Currency.USD)
                .balance(BigDecimal.valueOf(500.00))
                .build();

        List<Account> existingAccounts = new ArrayList<>();
        existingAccounts.add(existingAccount);

        when(accountDao.getAll()).thenReturn(existingAccounts);

        try (MockedStatic<IdManager> mockedIdManager = mockStatic(IdManager.class)) {
            mockedIdManager.when(() -> generateNextId(AppConfig.ACCOUNT_ID.getPath())).thenReturn(2);

            Account result = accountService.create(newAccount);

            assertEquals(2, result.getAccountId());

            ArgumentCaptor<List<Account>> listCaptor = ArgumentCaptor.forClass(List.class);
            verify(accountDao).save(listCaptor.capture());

            List<Account> savedAccounts = listCaptor.getValue();
            assertEquals(2, savedAccounts.size());
            assertEquals(1, savedAccounts.get(0).getAccountId());
            assertEquals(2, savedAccounts.get(1).getAccountId());
        }
    }

    @Test
    void findById_shouldReturnAccount_whenAccountExists() throws IOException {
        Account account1 = Account.builder()
                .accountId(1)
                .accountName("Checking")
                .currency(Currency.USD)
                .balance(BigDecimal.valueOf(1000.00))
                .build();

        Account account2 = Account.builder()
                .accountId(2)
                .accountName("Savings")
                .currency(Currency.EUR)
                .balance(BigDecimal.valueOf(500.00))
                .build();

        List<Account> existingAccounts = new ArrayList<>();
        existingAccounts.add(account1);
        existingAccounts.add(account2);

        when(accountDao.getAll()).thenReturn(existingAccounts);

        Optional<Account> result = accountService.findById(2);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().getAccountId());
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenAccountDoesNotExist() throws IOException {
        Account account = Account.builder()
                .accountId(1)
                .accountName("Checking")
                .currency(Currency.USD)
                .balance(BigDecimal.valueOf(1000.00))
                .build();

        List<Account> existingAccounts = new ArrayList<>();
        existingAccounts.add(account);

        when(accountDao.getAll()).thenReturn(existingAccounts);

        Optional<Account> result = accountService.findById(99);

        assertFalse(result.isPresent());
    }

    @Test
    void findById_shouldPropagateIOException_whenDaoThrowsIOException() throws IOException {
        when(accountDao.getAll()).thenThrow(new IOException("File not found"));

        assertThrows(IOException.class, () -> accountService.findById(1));
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenAccountListIsEmpty() throws IOException {
        List<Account> emptyList = new ArrayList<>();
        when(accountDao.getAll()).thenReturn(emptyList);

        Optional<Account> result = accountService.findById(1);

        assertFalse(result.isPresent());
    }

    @Test
    void listAll_shouldReturnAllAccounts_whenAccountsExist() throws IOException {
        Account account1 = Account.builder()
                .accountId(1)
                .accountName("Checking")
                .currency(Currency.USD)
                .balance(BigDecimal.valueOf(1000.00))
                .build();

        Account account2 = Account.builder()
                .accountId(2)
                .accountName("Savings")
                .currency(Currency.EUR)
                .balance(BigDecimal.valueOf(500.00))
                .build();

        List<Account> expectedAccounts = new ArrayList<>();
        expectedAccounts.add(account1);
        expectedAccounts.add(account2);

        when(accountDao.getAll()).thenReturn(expectedAccounts);

        List<Account> actualAccounts = accountService.listAll();

        assertEquals(2, actualAccounts.size());
        assertEquals(expectedAccounts, actualAccounts);
        verify(accountDao, times(1)).getAll();
    }

    @Test
    void listAll_shouldReturnEmptyList_whenNoAccountsExist() throws IOException {
        List<Account> emptyList = new ArrayList<>();
        when(accountDao.getAll()).thenReturn(emptyList);

        List<Account> result = accountService.listAll();

        assertTrue(result.isEmpty());
        verify(accountDao, times(1)).getAll();
    }

    @Test
    void listAll_shouldPropagateIOException_whenDaoThrowsIOException() throws IOException {
        when(accountDao.getAll()).thenThrow(new IOException("File not found"));

        assertThrows(IOException.class, () -> accountService.listAll());
        verify(accountDao, times(1)).getAll();
    }

}
