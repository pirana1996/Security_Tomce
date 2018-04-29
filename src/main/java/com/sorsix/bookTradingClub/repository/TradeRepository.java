package com.sorsix.bookTradingClub.repository;

import com.sorsix.bookTradingClub.domain.Book;
import com.sorsix.bookTradingClub.domain.Trade;
import com.sorsix.bookTradingClub.domain.TradeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jordancho on 26.7.2017.
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    @Transactional
    void deleteByTakenBookOrGivenBook(Book takenBook, Book givenBook);
}
