package com.sorsix.bookTradingClub.service;

import com.sorsix.bookTradingClub.domain.Book;
import com.sorsix.bookTradingClub.domain.BookDetails;
import com.sorsix.bookTradingClub.domain.BookPicture;
import com.sorsix.bookTradingClub.domain.User;
import com.sorsix.bookTradingClub.domain.dto.BookDto;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jordancho on 18.7.2017.
 */
public interface BookService {

    Book createBook(BookDto bookDto, Principal principal);

    Book updateBook(Book book);

    void removeBook(Long bookId);

    List<Book> getAllBooks(String sort);

    List<Book> getUserBooks(User user);

    List<Book> getBooksNotFromUser(User user);

    Book findBook(Long id);

    Book addBookPicture (Long bookId, MultipartFile image) throws IOException, SQLException;

    List<Book> searchBook(String name);
}
