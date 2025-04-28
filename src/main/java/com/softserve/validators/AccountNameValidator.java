package com.softserve.validators;

public class AccountNameValidator {

    private AccountNameValidator() {
    }

    /**
     * A regular expression pattern used to validate account names.
     * This pattern ensures that account names:
     * - Start with an alphabetic character (a-z or A-Z).
     * - May be followed by alphanumeric characters or spaces.
     * - Have a minimum length of 3 characters.
     */
    private static final String NAME_MATCH_REGEX = "^[a-zA-Z][a-zA-Z0-9 ]{2,17}$";


    public static boolean isValid(String accountName) {
        if (accountName == null || accountName.isBlank()) {
            return false;
        }
        return accountName.matches(NAME_MATCH_REGEX);
    }

    public static String validateAccountName(String accountName) {
        if (isValid(accountName)) {
            return accountName;
        }
        throw new IllegalArgumentException("Invalid account name: " + accountName);
    }
}
