package com.sorsix.bookTradingClub.service;

import com.sorsix.bookTradingClub.domain.Book;
import com.sorsix.bookTradingClub.domain.Trade;
import com.sorsix.bookTradingClub.domain.User;

/**
 * Created by jordancho on 26.7.2017.
 */

public interface TradeService {

    Trade saveTrade(User user, String givenBook, String takenBook);

}
