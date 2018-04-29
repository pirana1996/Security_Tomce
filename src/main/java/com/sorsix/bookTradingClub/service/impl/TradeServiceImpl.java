package com.sorsix.bookTradingClub.service.impl;

import com.sorsix.bookTradingClub.domain.Book;
import com.sorsix.bookTradingClub.domain.Trade;
import com.sorsix.bookTradingClub.domain.User;
import com.sorsix.bookTradingClub.repository.TradeRepository;
import com.sorsix.bookTradingClub.service.TradeService;
import org.springframework.stereotype.Service;

/**
 * Created by jordancho on 26.7.2017.
 */
@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }


    @Override
    public Trade saveTrade(User user, String givenBook, String takenBook) {
        Trade trade = new Trade(user, givenBook, takenBook);
        return tradeRepository.save(trade);
    }
}
