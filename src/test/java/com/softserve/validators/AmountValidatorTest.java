package com.softserve.validators;

import com.softserve.models.category.Category;
import com.softserve.models.category.CategoryType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;


class AmountValidatorTest {

    @Nested
    @DisplayName("Amount Validation for Positive Amounts")
    class PositiveAmountValidation {
        @DisplayName("Should validate positive amount for income category")
        @ParameterizedTest
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

        @DisplayName("Should reject zero amount for income")
        @Test
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
        @DisplayName("Should validate negative amounts for expense")
        @ParameterizedTest
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

        @DisplayName("Should reject positive amount for expense")
        @Test
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

    @Nested
    @DisplayName("Invalid Amount Parsing")
    class InvalidAmountParsing {
        @DisplayName("Should throw parsing exception for invalid number formats")
        @ParameterizedTest
        @ValueSource(strings = {
                "abc",
                "12.34.56",
                "+",
                "-",
                "1,000.00"
        })
        void validateAmount_shouldThrowParsingException_whenAmountIsInvalid(String invalidAmount) {

            Category categoryMock = createMockCategory(CategoryType.INCOME, true);

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> AmountValidator.validateAmount(invalidAmount, categoryMock),
                    "Invalid number format should throw parsing exception"
            );
            assertEquals("Invalid amount: must be a valid non-null number.",
                    exception.getMessage());
        }

        @DisplayName("Should throw parsing exception for null or empty input")
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t", "\n"})
        void validateAmount_shouldThrowParsingException_whenAmountIsNullOrEmpty(String invalidAmount) {

            Category categoryMock = createMockCategory(CategoryType.INCOME, true);

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> AmountValidator.validateAmount(invalidAmount, categoryMock),
                    "Null or empty amount should throw parsing exception"
            );
            assertEquals("Invalid amount: must be a valid non-null number.",
                    exception.getMessage());
        }

        @DisplayName("Should handle different number formats")
        @ParameterizedTest
        @CsvSource({
                "1000",
                "1000.00",
                "+1000.00",
                "1,000.00",
                " 1000.00 "
        })
        void validateAmount_shouldHandleDifferentNumberFormats(String amount) {

            Category categoryMock = createMockCategory(CategoryType.INCOME, true);

            assertDoesNotThrow(
                    () -> AmountValidator.validateAmount(amount, categoryMock),
                    "Should handle different number formats"
            );
        }
    }

    @Nested
    @DisplayName("Precision and Edge Cases")
    class PrecisionTests {
        @DisplayName("Should handle different decimal precisions")
        @Test
        void validateAmount_shouldHandleDifferentDecimalPrecisions() {

            String[] amounts = {"100", "100.0", "100.00", "100.000"};
            Category incomeCategoryMock = createMockCategory(CategoryType.INCOME, true);

            for (String amount : amounts) {
                BigDecimal result = AmountValidator.validateAmount(amount, incomeCategoryMock);
                assertEquals(new BigDecimal(amount), result,
                        "Should maintain exact decimal representation");
            }
        }
    }


    private Category createMockCategory(CategoryType type, boolean isValidAmount) {
        Category mockCategory = mock(Category.class);
        when(mockCategory.getCategoryType()).thenReturn(type);
        when(mockCategory.isValidAmount(any(BigDecimal.class))).thenReturn(isValidAmount);
        return mockCategory;
    }

}