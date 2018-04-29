package com.sorsix.bookTradingClub.repository;

import com.sorsix.bookTradingClub.domain.Book;
import com.sorsix.bookTradingClub.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by jordancho on 18.7.2017.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByUser(User user);

    List<Book> findAll(Sort sort);

    List<Book> findBookByNameLikeIgnoreCase(String name);

 }
