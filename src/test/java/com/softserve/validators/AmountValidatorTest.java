package com.softserve.validators;

import com.softserve.models.category.Category;
import com.softserve.models.category.CategoryType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;


class AmountValidatorTest {

    @Nested
    @DisplayName("Amount Validation for Positive Amounts")
    class PositiveAmountValidation {
        @ParameterizedTest
        @DisplayName("Should validate positive amount for income category")
        @CsvSource({
                "150.00",
                "1000.50",
                "0.01"
        })
        void validateAmount_shouldReturnParsedAmount_whenIncomeCategoryAndPositiveAmount(String validAmount) {
            Category incomeCategoryMock = createMockCategory(CategoryType.INCOME, true);

            BigDecimal result = AmountValidator.validateAmount(validAmount, incomeCategoryMock);

            assertNotNull(result);
            assertTrue(result.compareTo(BigDecimal.ZERO) > 0,
                    "Income amount should be strictly positive");
            assertEquals(new BigDecimal(validAmount), result,
                    "Parsed amount should match input exactly");
            verify(incomeCategoryMock).isValidAmount(
                    argThat(bd -> bd.compareTo(new BigDecimal(validAmount)) == 0)
            );
        }

        @Test
        @DisplayName("Should reject zero amount for income")
        void validateAmount_shouldThrowException_whenIncomeCategoryAndZeroAmount() {

            String zeroAmount = "0.00";
            Category incomeCategoryMock = createMockCategory(CategoryType.INCOME, false);

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> AmountValidator.validateAmount(zeroAmount, incomeCategoryMock),
                    "Zero amount should be rejected for income"
            );
            assertEquals("Invalid amount: income should be positive and expenses should be negative.",
                    exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Negative Amount Validation for Expenses")
    class NegativeExpenseAmountValidation {
        @ParameterizedTest
        @DisplayName("Should validate negative amounts for expense")
        @CsvSource({
                "-200.00",
                "-50.75",
                "-0.01"
        })
        void validateAmount_shouldReturnParsedAmount_whenExpenseCategoryAndNegativeAmount(String validAmount) {

            Category expenseCategoryMock = createMockCategory(CategoryType.EXPENSE, true);

            BigDecimal result = AmountValidator.validateAmount(validAmount, expenseCategoryMock);

            assertNotNull(result);
            assertTrue(result.compareTo(BigDecimal.ZERO) <= 0,
                    "Expense amount should be negative or zero");
            assertEquals(new BigDecimal(validAmount), result,
                    "Parsed amount should match input exactly");
            verify(expenseCategoryMock).isValidAmount(
                    argThat(bd -> bd.compareTo(new BigDecimal(validAmount)) == 0)
            );
        }

        @Test
        @DisplayName("Should reject positive amount for expense")
        void validateAmount_shouldThrowException_whenExpenseCategoryAndPositiveAmount() {

            String positiveAmount = "100.00";
            Category expenseCategoryMock = createMockCategory(CategoryType.EXPENSE, false);

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> AmountValidator.validateAmount(positiveAmount, expenseCategoryMock),
                    "Positive amount should be rejected for expense"
            );
            assertEquals("Invalid amount: income should be positive and expenses should be negative.",
                    exception.getMessage());
        }
    }

    private Category createMockCategory(CategoryType type, boolean isValidAmount) {
        Category mockCategory = mock(Category.class);
        when(mockCategory.getCategoryType()).thenReturn(type);
        when(mockCategory.isValidAmount(any(BigDecimal.class))).thenReturn(isValidAmount);
        return mockCategory;
    }

}