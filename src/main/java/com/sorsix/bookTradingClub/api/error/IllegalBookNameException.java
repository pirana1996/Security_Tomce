package com.sorsix.bookTradingClub.api.error;

/**
 * Created by jordancho on 03.8.2017.
 */
public class IllegalBookNameException extends RuntimeException {
    public IllegalBookNameException(String message) {
        super(message);
    }
}
