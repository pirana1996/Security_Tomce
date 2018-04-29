package com.sorsix.bookTradingClub.repository;

import com.sorsix.bookTradingClub.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jordancho on 31.7.2017.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAuthorByNameLikeIgnoreCase(String name);

    Author findByName(String name);
}
