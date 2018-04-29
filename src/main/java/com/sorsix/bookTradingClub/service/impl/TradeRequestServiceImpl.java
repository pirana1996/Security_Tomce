package com.sorsix.bookTradingClub.service.impl;

import com.sorsix.bookTradingClub.domain.Book;
import com.sorsix.bookTradingClub.domain.Trade;
import com.sorsix.bookTradingClub.domain.TradeRequest;
import com.sorsix.bookTradingClub.domain.User;
import com.sorsix.bookTradingClub.domain.dto.TradeRequestDto;
import com.sorsix.bookTradingClub.repository.BookRepository;
import com.sorsix.bookTradingClub.repository.TradeRepository;
import com.sorsix.bookTradingClub.repository.TradeRequestRepository;
import com.sorsix.bookTradingClub.repository.UserRepository;
import com.sorsix.bookTradingClub.service.TradeRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jordancho on 26.7.2017.
 */
@Service
public class TradeRequestServiceImpl implements TradeRequestService {

    private final TradeRequestRepository tradeRequestRepository;
    private final TradeRepository tradeRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final Logger logger = LoggerFactory.getLogger(TradeRequestServiceImpl.class);

    public TradeRequestServiceImpl(TradeRequestRepository tradeRequestRepository,
                                   TradeRepository tradeRepository,
                                   UserRepository userRepository,
                                   BookRepository bookRepository) {
        this.tradeRequestRepository = tradeRequestRepository;
        this.tradeRepository = tradeRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public TradeRequest createTradeRequest(TradeRequestDto tradeRequestDto, String username) {
        User user = (User) userRepository.findByUsername(username);
        Book userBook = bookRepository.findOne(tradeRequestDto.giveBookId);
        Book targetBook = bookRepository.findOne(tradeRequestDto.takenBookId);
        TradeRequest tradeRequest = new TradeRequest(user, userBook, targetBook);
        return this.tradeRequestRepository.save(tradeRequest);
    }

    @Transactional
    @Override
    public List<TradeRequest> getTradeRequestsForUser(String username) {
        User user = (User) userRepository.findByUsername(username);
        return this.tradeRequestRepository.findByUser(user);
    }

    @Override
    public List<TradeRequest> getAllTradeRequests() {
        return (List<TradeRequest>) this.tradeRequestRepository.findAll();
    }

    @Override
    public void removeTradeRequest(Long tradeRequestId) {
        TradeRequest tradeRequest = tradeRequestRepository.findOne(tradeRequestId);
        this.tradeRequestRepository.delete(tradeRequest);
    }

    @Override
    public TradeRequest findById(Long id) {
        return this.tradeRequestRepository.findOne(id);
    }

    @Override
    public void acceptTradeRequest(Long tradeRequestId) {
        TradeRequest tradeRequest = tradeRequestRepository.findOne(tradeRequestId);
        User requestSendBy = tradeRequest.user;
        User requestSendTo = tradeRequest.takenBook.user;
        Book givenBook = tradeRequest.givenBook;
        Book takenBook = tradeRequest.takenBook;
        givenBook.user = requestSendTo;
        takenBook.user = requestSendBy;
        bookRepository.save(givenBook);
        bookRepository.save(takenBook);
        logger.info("Books has been traded");
        tradeRequestRepository.deleteByTakenBookOrGivenBook(takenBook, takenBook);
        tradeRequestRepository.deleteByTakenBookOrGivenBook(givenBook, givenBook);
        logger.info("Trade has been saved");
        Trade trade = new Trade(requestSendBy, givenBook, takenBook);
        tradeRepository.save(trade);
    }

    @Transactional
    @Override
    public List<TradeRequest> findByTradeRequestUser(String username) {
        User user = (User) userRepository.findByUsername(username);
        return tradeRequestRepository.findByTakenBookUser(user);
    }

}
