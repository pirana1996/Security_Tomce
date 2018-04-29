package com.sorsix.bookTradingClub.api;

import com.sorsix.bookTradingClub.domain.Book;
import com.sorsix.bookTradingClub.domain.TradeRequest;
import com.sorsix.bookTradingClub.domain.User;
import com.sorsix.bookTradingClub.domain.dto.BookDto;
import com.sorsix.bookTradingClub.domain.dto.TradeRequestDto;
import com.sorsix.bookTradingClub.service.BookService;
import com.sorsix.bookTradingClub.service.TradeRequestService;
import com.sorsix.bookTradingClub.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jordancho on 10.8.2017.
 */
@RestController
@RequestMapping(value = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final BookService bookService;
    private final TradeRequestService tradeRequestService;
    private final UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    public BookController(BookService bookService,
                          TradeRequestService tradeRequestService,
                          UserService userService) {
        this.bookService = bookService;
        this.tradeRequestService = tradeRequestService;
        this.userService = userService;
    }

    @PostMapping(value = "/{bookId}/images/upload")
    public void addImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Long bookId) throws IOException, SQLException {
        bookService.addBookPicture(bookId, image);
    }

    @GetMapping(value = "/custom")
    public List<Book> getBooksNotFromUser(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return this.bookService.getBooksNotFromUser(user);
    }

    @PostMapping
    public Book addBook(@RequestBody @Valid BookDto bookDto,
                        Principal principal) {
        return bookService.createBook(bookDto, principal);
    }

    @PostMapping(value = "/trade")
    public TradeRequest createTradeRequest(@RequestBody TradeRequestDto tradeRequestDto,
                                           Principal principal) {
        return tradeRequestService.createTradeRequest(tradeRequestDto, principal.getName());
    }

    @DeleteMapping(value = "/trade/{tradeRequestId}")
    public void removeTradeRequest(@PathVariable Long tradeRequestId) {
        this.tradeRequestService.removeTradeRequest(tradeRequestId);
    }

    @PostMapping(value = "/trade/{tradeRequestId}")
    public void acceptTradeRequest(@PathVariable Long tradeRequestId) {
        tradeRequestService.acceptTradeRequest(tradeRequestId);
    }


    @GetMapping(value = "/trade/requests/sent")
    public List<TradeRequest> getTradeRequestSentByUser(Principal principal) {
        return this.tradeRequestService.getTradeRequestsForUser(principal.getName());
    }

    @GetMapping(value = "/trade/requests")
    public List<TradeRequest> getTradeRequestForUser(Principal principal) {
        return tradeRequestService.findByTradeRequestUser(principal.getName());
    }

    @DeleteMapping(value = "/{bookId}")
    public void removeBook(@PathVariable Long bookId) {
        bookService.removeBook(bookId);
    }

}
