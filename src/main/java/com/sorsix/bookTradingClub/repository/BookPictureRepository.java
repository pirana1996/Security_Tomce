package com.sorsix.bookTradingClub.repository;

import com.sorsix.bookTradingClub.domain.BookPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by jordancho on 18.7.2017.
 */
@Repository
public interface BookPictureRepository extends JpaRepository<BookPicture, Long> {
}
