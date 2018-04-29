package com.sorsix.bookTradingClub.service;

import com.sorsix.bookTradingClub.domain.Author;

import java.util.List;

/**
 * Created by jordancho on 31.7.2017.
 */
public interface AuthorService {

    Author createAuthor(String name);

    List<Author> getAllAuthors();

    Author getAuthorById(Long id);

    List<Author> searchAuthor(String name);

    Author findByName(String name);
}
