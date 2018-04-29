package com.sorsix.bookTradingClub.service;

import com.sorsix.bookTradingClub.domain.BookDetails;

/**
 * Created by jordancho on 18.7.2017.
 */
public interface BookDetailsService {

    BookDetails createBookDetails(String description, Long bookId);

    BookDetails getBookDetailsByBookId (Long bookId);

    BookDetails updateBookDetails(Long bookId, String description);

    void removeBookDetails(BookDetails bookDetails);
}
