package com.sorsix.bookTradingClub.service;

import com.sorsix.bookTradingClub.domain.Book;
import com.sorsix.bookTradingClub.domain.Trade;
import com.sorsix.bookTradingClub.domain.TradeRequest;
import com.sorsix.bookTradingClub.domain.User;
import com.sorsix.bookTradingClub.domain.dto.TradeRequestDto;

import java.util.List;

/**
 * Created by jordancho on 26.7.2017.
 */
public interface TradeRequestService {

    TradeRequest createTradeRequest(TradeRequestDto tradeRequestDto, String username);

    List<TradeRequest> getTradeRequestsForUser(String username);

    List<TradeRequest> getAllTradeRequests();

    void removeTradeRequest(Long tradeRequestId);

    TradeRequest findById(Long id);

    void acceptTradeRequest(Long tradeRequestId);

    List<TradeRequest> findByTradeRequestUser(String username);
}
