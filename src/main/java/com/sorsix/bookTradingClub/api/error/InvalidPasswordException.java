package com.sorsix.bookTradingClub.api.error;

/**
 * Created by jordancho on 03.8.2017.
 */
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
