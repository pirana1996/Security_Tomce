package com.sorsix.bookTradingClub.repository;

import com.sorsix.bookTradingClub.domain.Book;
import com.sorsix.bookTradingClub.domain.TradeRequest;
import com.sorsix.bookTradingClub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by jordancho on 26.7.2017.
 */
@Repository
public interface TradeRequestRepository extends JpaRepository<TradeRequest, Long> {

    List<TradeRequest> findByUser(User user);

    List<TradeRequest> findByTakenBookUser(User user);

    @Transactional
    List<TradeRequest> deleteByTakenBookOrGivenBook(Book takenBook, Book givenBook);

    Optional<Long> countByTakenBookOrGivenBook(Book takenBook, Book givenBook);
}
