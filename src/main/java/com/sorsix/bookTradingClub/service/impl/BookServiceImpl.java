package com.sorsix.bookTradingClub.service.impl;

import com.sorsix.bookTradingClub.api.error.IllegalBookNameException;
import com.sorsix.bookTradingClub.domain.*;
import com.sorsix.bookTradingClub.domain.dto.BookDto;
import com.sorsix.bookTradingClub.repository.*;
import com.sorsix.bookTradingClub.service.BookService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jordancho on 18.7.2017.
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookPictureRepository bookPictureRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final BookDetailsRepository bookDetailsRepository;
    private final TradeRequestRepository tradeRequestRepository;
    private final TradeRepository tradeRepository;

    public BookServiceImpl(
            BookRepository bookRepository,
            BookPictureRepository bookPictureRepository,
            UserRepository userRepository,
            AuthorRepository authorRepository,
            BookDetailsRepository bookDetailsRepository,
            TradeRequestRepository tradeRequestRepository,
            TradeRepository tradeRepository) {
        this.bookRepository = bookRepository;
        this.bookPictureRepository = bookPictureRepository;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
        this.bookDetailsRepository = bookDetailsRepository;
        this.tradeRequestRepository = tradeRequestRepository;
        this.tradeRepository = tradeRepository;
    }


    @Override
    public Book createBook(BookDto bookDto, Principal principal) {
        if (bookDto.name.length() < 3)
            throw new IllegalBookNameException("Illegal book name");
        Book book = new Book(bookDto.name);
        List<String> authors = bookDto.authors;
        List<Author> bookAuthors = new ArrayList<>();
        authors.forEach(author -> {
            Author createdAuthor = authorRepository.findByName(author);
            if (createdAuthor == null) {
                createdAuthor = authorRepository.save(new Author(author));
            }
            bookAuthors.add(createdAuthor);
        });
        Book savedBook = bookRepository.save(book);
        savedBook.authors = bookAuthors;
        BookDetails bookDetails = new BookDetails(bookDto.description, savedBook);
        bookDetailsRepository.save(bookDetails);
        User user = (User) userRepository.findByUsername(principal.getName());
        savedBook.user = user;
        return bookRepository.save(savedBook);
    }

    @Override
    public Book updateBook(Book book) {
        Book old = bookRepository.findOne(book.id);
        old.name = book.name;
        old.user = book.user;
        return bookRepository.save(old);
    }

    @Override
    @Transactional
    public void removeBook(Long bookId) {
        Book book = bookRepository.findOne(bookId);
        tradeRequestRepository.deleteByTakenBookOrGivenBook(book, book);
        tradeRepository.deleteByTakenBookOrGivenBook(book, book);
        bookRepository.delete(book);
    }

    @Transactional
    @Override
    public List<Book> getAllBooks(String sort) {
        List<Book> books = new ArrayList<>();
        switch (sort) {
            case "name":
                books = bookRepository.findAll(orderByName());
                break;
            default:
                books = bookRepository.findAll(orderById());
                break;
        }
        return books;
    }

    @Transactional
    @Override
    public List<Book> getUserBooks(User user) {
        return bookRepository.findByUser(user);
    }

    @Override
    public List<Book> getBooksNotFromUser(User user) {
        return this.getAllBooks("id").stream().filter(book ->
                (!(book.user.username == user.username) && (isInTradeRequest(book) == 0l))).collect(Collectors.toList());
    }

    private Long isInTradeRequest(Book book) {
        return this.tradeRequestRepository.countByTakenBookOrGivenBook(book, book)
                .orElse(0l);
    }

    @Transactional
    @Override
    public Book findBook(Long id) {
        return bookRepository.findOne(id);
    }

    @Override
    public Book addBookPicture(Long bookId, MultipartFile image) throws IOException, SQLException {
        Book book = bookRepository.findOne(bookId);
        FileEmbeddable fileEmbeddable = new FileEmbeddable(image.getBytes(), image.getName(), image.getContentType(), (int) image.getSize());
        BookPicture bookPicture = new BookPicture(fileEmbeddable);
        bookPictureRepository.save(bookPicture);
        book.bookPicture = bookPicture;
        book.bookPicture = bookPicture;
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public List<Book> searchBook(String name) {
        return this.bookRepository.findBookByNameLikeIgnoreCase("%" + name + "%");
    }

    private Sort orderById() {
        return new Sort(Sort.Direction.ASC, "id");
    }

    private Sort orderByName() {
        return new Sort(Sort.Direction.ASC, "name");
    }
}
