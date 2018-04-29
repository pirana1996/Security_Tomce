package com.sorsix.bookTradingClub.service.impl;

import com.sorsix.bookTradingClub.domain.Author;
import com.sorsix.bookTradingClub.repository.AuthorRepository;
import com.sorsix.bookTradingClub.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jordancho on 31.7.2017.
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author createAuthor(String name) {
        if (authorRepository.findByName(name) == null) {
            Author author = new Author(name);
            return authorRepository.save(author);
        } else {
            return authorRepository.findByName(name);
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.findOne(id);
    }

    @Override
    public List<Author> searchAuthor(String name) {
        return authorRepository.findAuthorByNameLikeIgnoreCase("%" + name + "%");
    }

    @Override
    public Author findByName(String name) {
        return authorRepository.findByName(name);
    }
}
