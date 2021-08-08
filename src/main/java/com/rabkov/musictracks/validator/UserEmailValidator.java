package com.rabkov.musictracks.validator;

public class UserEmailValidator {
    private static final String EMAIL_REGEX = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";

    private UserEmailValidator() {
    }

    public static boolean isValid(String email){
        return email.matches(EMAIL_REGEX);
    }
}
