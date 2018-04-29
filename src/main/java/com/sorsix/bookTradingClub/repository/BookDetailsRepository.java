package com.sorsix.bookTradingClub.repository;

import com.sorsix.bookTradingClub.domain.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jordancho on 18.7.2017.
 */
@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetails, Long> {

    BookDetails findByBookId(Long id);
}
