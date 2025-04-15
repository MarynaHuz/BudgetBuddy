package com.softserve.validators;

public class AccountNameValidator {

    private AccountNameValidator() {
    }
    private static final String NAME_MATCH_REGEX =
            "^[a-zA-Z]" +
            "(?=.*\\b[a-zA-Z0-9]{3,}\\b)" +
            "[a-zA-Z0-9 ]+$";

    public static boolean isValid(String accountName){
        if (accountName == null || accountName.isBlank()) {
            return false;
        }
        return accountName.matches(NAME_MATCH_REGEX);
    }

    public static String validateAccountName(String accountName){
        if(isValid(accountName)){
            return accountName;
        }
        throw new IllegalArgumentException("Invalid account name: " + accountName);
    }
}
