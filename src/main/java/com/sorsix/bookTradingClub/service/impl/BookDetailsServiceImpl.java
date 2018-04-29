package com.sorsix.bookTradingClub.service.impl;

import com.sorsix.bookTradingClub.domain.Book;
import com.sorsix.bookTradingClub.domain.BookDetails;
import com.sorsix.bookTradingClub.repository.BookDetailsRepository;
import com.sorsix.bookTradingClub.repository.BookRepository;
import com.sorsix.bookTradingClub.service.BookDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by jordancho on 18.7.2017.
 */
@Service
public class BookDetailsServiceImpl implements BookDetailsService {

    private final BookDetailsRepository bookDetailsRepository;
    private final BookRepository bookRepository;

    public BookDetailsServiceImpl(BookDetailsRepository bookDetailsRepository, BookRepository bookRepository) {
        this.bookDetailsRepository = bookDetailsRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    public BookDetails createBookDetails(String description, Long bookId) {
        Book book = bookRepository.findOne(bookId);
        BookDetails bookDetails = new BookDetails(description, book);
        return bookDetailsRepository.save(bookDetails);
    }

    @Override
    public BookDetails getBookDetailsByBookId(Long bookId) {
        return bookDetailsRepository.findByBookId(bookId);
    }


    @Override
    public BookDetails updateBookDetails(Long bookId, String description) {
        BookDetails bookDetails = bookDetailsRepository.findByBookId(bookId);
        bookDetails.description = description;
        return bookDetailsRepository.save(bookDetails);
    }

    @Override
    public void removeBookDetails(BookDetails bookDetails) {
        bookDetailsRepository.delete(bookDetails);
    }
}
