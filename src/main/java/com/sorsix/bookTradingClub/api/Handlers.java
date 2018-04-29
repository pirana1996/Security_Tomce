package com.sorsix.bookTradingClub.api;

import com.sorsix.bookTradingClub.api.error.IllegalBookNameException;
import com.sorsix.bookTradingClub.api.error.InvalidPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jordancho on 15.8.2017.
 */

@RestControllerAdvice
public class Handlers {

    private Logger logger = LoggerFactory.getLogger(Handlers.class);

    @ExceptionHandler(InvalidPasswordException.class)
    public Map<String, String> onInvalidPassword(InvalidPasswordException e) {
        logger.info("ERROR = " + e.getMessage());
        return Collections.singletonMap("error", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> onInvalidUser(MethodArgumentNotValidException e) {
        int size = e.getMessage().split(";").length;
        logger.info("ERROR = " + e.getMessage().split(";")[size - 1]);
        return Collections.singletonMap("error", e.getMessage().split(";")[size - 1].replace("default message ", ""));
    }

    @ExceptionHandler(IllegalBookNameException.class)
    public Map<String, String> onIllegalBookName(IllegalBookNameException e) {
        logger.debug("Illegal book name");
        return Collections.singletonMap("error", e.getMessage());
    }
}

