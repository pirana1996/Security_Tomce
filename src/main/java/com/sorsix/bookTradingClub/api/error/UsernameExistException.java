package com.sorsix.bookTradingClub.api.error;

/**
 * Created by jordancho on 01.8.2017.
 */
public class UsernameExistException extends RuntimeException{
    public UsernameExistException(String message) {
        super(message);
    }
}
