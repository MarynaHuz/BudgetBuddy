package com.softserve.validators;

public class AccountNameValidator {

    private AccountNameValidator() {
    }
    private static final String NAME_MATCH_REGEX = """
            ^[a-zA-Z]
            (?=.*\\b[a-zA-Z0-9]{3,}\\b)
            [a-zA-Z0-9 ]+$
            """;


}
