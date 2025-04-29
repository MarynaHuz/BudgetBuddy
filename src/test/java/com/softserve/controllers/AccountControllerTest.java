package com.softserve.controllers;

import com.softserve.formatters.AccountFormatter;
import com.softserve.formatters.ErrorFormatter;
import com.softserve.models.account.Account;
import com.softserve.models.account.Currency;
import com.softserve.services.AccountService;
import com.softserve.utils.JsonUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    private AccountController accountController;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private MockedStatic<ErrorFormatter> errorFormatterMock;
    private MockedStatic<AccountFormatter> accountFormatterMock;
    private MockedStatic<JsonUtil> jsonUtilMock;

    @BeforeEach
    void setUp() {
        accountController = new AccountController(accountService);
        System.setOut(new PrintStream(outputStream));
        errorFormatterMock = mockStatic(ErrorFormatter.class);
        accountFormatterMock = mockStatic(AccountFormatter.class);
        jsonUtilMock = mockStatic(JsonUtil.class);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        errorFormatterMock.close();
        accountFormatterMock.close();
        jsonUtilMock.close();
    }

    @Test
    void create_shouldPrintFormattedAccount_whenParametersAreValid() throws IOException {
        List<String> validAccountParams = Arrays.asList("Test Account", "USD", "100.00");
        Account expectedAccount = new Account();
        expectedAccount.setAccountName("Test Account");
        expectedAccount.setCurrency(Currency.USD);
        expectedAccount.setBalance(new BigDecimal("100.00"));

        when(accountService.create(any(Account.class))).thenReturn(expectedAccount);
        accountFormatterMock.when(() -> AccountFormatter.formatAccount(any(Account.class)))
                .thenReturn("Formatted Account");

        accountController.create(validAccountParams);

        verify(accountService, times(1)).create(any(Account.class));
        accountFormatterMock.verify(() -> AccountFormatter.formatAccount(any(Account.class)));
        assertEquals("Formatted Account" + System.lineSeparator(), outputStream.toString());

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountService).create(accountCaptor.capture());
        Account capturedAccount = accountCaptor.getValue();
        assertEquals("Test Account", capturedAccount.getAccountName());
        assertEquals(Currency.USD, capturedAccount.getCurrency());
        assertEquals(new BigDecimal("100.00"), capturedAccount.getBalance());
    }

    @Test
    void create_shouldDisplayError_whenParametersAreMissing() throws IOException {
        List<String> invalidAccountParams = Arrays.asList("Test Account", "USD");

        accountController.create(invalidAccountParams);

        verify(accountService, never()).create(any(Account.class));
        errorFormatterMock.verify(() -> ErrorFormatter.displayError(contains("Validation error")));
    }

    @Test
    void create_shouldDisplayError_whenAccountNameIsInvalid() throws IOException {
        List<String> invalidNameParams = Arrays.asList("", "USD", "100.00");

        accountController.create(invalidNameParams);

        verify(accountService, never()).create(any(Account.class));
        errorFormatterMock.verify(() -> ErrorFormatter.displayError(contains("Validation error")));
    }

    @Test
    void create_shouldDisplayError_whenCurrencyCodeIsInvalid() throws IOException {
        List<String> invalidCurrencyParams = Arrays.asList("Test Account", "XYZ", "100.00");

        accountController.create(invalidCurrencyParams);

        verify(accountService, never()).create(any(Account.class));
        errorFormatterMock.verify(() -> ErrorFormatter.displayError(contains("Validation error")));
    }

    @Test
    void create_shouldDisplayError_whenBalanceIsInvalid() throws IOException {
        List<String> invalidBalanceParams = Arrays.asList("Test Account", "USD", "invalid");

        accountController.create(invalidBalanceParams);

        verify(accountService, never()).create(any(Account.class));
        errorFormatterMock.verify(() -> ErrorFormatter.displayError(contains("Validation error")));
    }

    @Test
    void create_shouldDisplayError_whenIOExceptionOccurs() throws IOException {
        List<String> validAccountParams = Arrays.asList("Test Account", "USD", "100.00");

        when(accountService.create(any(Account.class))).thenThrow(new IOException("Failed to save"));

        accountController.create(validAccountParams);

        verify(accountService, times(1)).create(any(Account.class));
        errorFormatterMock.verify(() -> ErrorFormatter.displayError(contains("Error saving account")));
    }

    @Test
    void findById_shouldPrintAccount_whenAccountExists() throws IOException {
        String validId = "123";
        int parsedId = 123;
        Account account = new Account();
        String formattedAccount = "Formatted Account Details";

        when(accountService.findById(parsedId)).thenReturn(Optional.of(account));
        accountFormatterMock.when(() -> AccountFormatter.formatAccount(account))
                .thenReturn(formattedAccount);

        accountController.findById(validId);

        verify(accountService).findById(parsedId);
        accountFormatterMock.verify(() -> AccountFormatter.formatAccount(account));

        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("The account has been found:"));
        assertTrue(consoleOutput.contains(formattedAccount));
        errorFormatterMock.verify(() -> ErrorFormatter.displayError(anyString()), never());
    }

    @Test
    void findById_shouldDisplayError_whenAccountDoesNotExist() throws IOException {
        String validId = "123";
        int parsedId = 123;
        String expectedErrorMsg = "Account with ID 123 hasn't been found!";

        when(accountService.findById(parsedId)).thenReturn(Optional.empty());

        accountController.findById(validId);

        verify(accountService).findById(parsedId);
        errorFormatterMock.verify(() -> ErrorFormatter.displayError(eq(expectedErrorMsg)));
    }

    @Test
    void findById_shouldDisplayError_whenIdIsInvalid() throws IOException {
        String invalidId = "abc";
        String expectedErrorMsg = "ID must be a positive number";

        accountController.findById(invalidId);

        verify(accountService, never()).findById(anyInt());
        errorFormatterMock.verify(() -> ErrorFormatter.displayError(eq(expectedErrorMsg)));
    }

    @Test
    void findById_shouldDisplayError_whenIOExceptionOccurs() throws IOException {
        String validId = "123";
        int parsedId = 123;
        String errorMessage = "File not found or cannot be read";

        when(accountService.findById(parsedId)).thenThrow(new IOException(errorMessage));

        accountController.findById(validId);

        verify(accountService).findById(parsedId);
        errorFormatterMock.verify(() -> ErrorFormatter.displayError(eq(errorMessage)));
    }

    @Test
    void findById_shouldDisplayError_whenIdIsZero() throws IOException {
        String invalidId = "0";
        String expectedErrorMsg = "ID must be a positive number";

        accountController.findById(invalidId);

        verify(accountService, never()).findById(anyInt());
        errorFormatterMock.verify(() -> ErrorFormatter.displayError(eq(expectedErrorMsg)));
    }

    @Test
    void findById_shouldDisplayError_whenIdIsNegative() throws IOException {
        String invalidId = "-5";
        String expectedErrorMsg = "ID must be a positive number";

        accountController.findById(invalidId);

        verify(accountService, never()).findById(anyInt());
        errorFormatterMock.verify(() -> ErrorFormatter.displayError(eq(expectedErrorMsg)));
    }

    @Test
    void listAll_shouldPrintFormattedAccountTable_whenAccountsExist() throws IOException {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        accounts.add(new Account());
        String formattedTable = "Formatted Table with Accounts";

        when(accountService.listAll()).thenReturn(accounts);
        accountFormatterMock.when(() -> AccountFormatter.formatAccountTable(accounts))
                .thenReturn(formattedTable);

        accountController.listAll();

        verify(accountService, times(1)).listAll();
        accountFormatterMock.verify(() -> AccountFormatter.formatAccountTable(accounts),
                times(1));
        assertEquals(formattedTable + System.lineSeparator(), outputStream.toString());
        errorFormatterMock.verify(() -> ErrorFormatter.displayError(anyString()), never());
    }

    @Test
    void listAll_shouldPrintEmptyTable_whenNoAccountsExist() throws IOException {
        List<Account> emptyList = Collections.emptyList();
        String emptyTableFormat = "Empty Table Headers";

        when(accountService.listAll()).thenReturn(emptyList);
        accountFormatterMock.when(() -> AccountFormatter.formatAccountTable(emptyList))
                .thenReturn(emptyTableFormat);

        accountController.listAll();

        verify(accountService, times(1)).listAll();
        accountFormatterMock.verify(() -> AccountFormatter.formatAccountTable(emptyList),
                times(1));
        assertEquals(emptyTableFormat + System.lineSeparator(), outputStream.toString());
        errorFormatterMock.verify(() -> ErrorFormatter.displayError(anyString()), never());
    }

    @Test
    void listAll_shouldDisplayError_whenIOExceptionOccurs() throws IOException {
        String errorMessage = "File not found or cannot be read";
        String expectedErrorDisplay = "Error retrieving accounts: " + errorMessage;

        when(accountService.listAll()).thenThrow(new IOException(errorMessage));

        accountController.listAll();

        verify(accountService, times(1)).listAll();
        accountFormatterMock.verify(() -> AccountFormatter.formatAccountTable(any()), never());
        errorFormatterMock.verify(() -> ErrorFormatter.displayError(eq(expectedErrorDisplay)),
                times(1));
        assertEquals("", outputStream.toString());
    }

}
