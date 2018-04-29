package com.sorsix.bookTradingClub.api;

import com.sorsix.bookTradingClub.api.error.UsernameExistException;
import com.sorsix.bookTradingClub.domain.Book;
import com.sorsix.bookTradingClub.domain.BookDetails;
import com.sorsix.bookTradingClub.domain.BookPicture;
import com.sorsix.bookTradingClub.domain.User;
import com.sorsix.bookTradingClub.service.BookDetailsService;
import com.sorsix.bookTradingClub.service.BookPictureService;
import com.sorsix.bookTradingClub.service.BookService;
import com.sorsix.bookTradingClub.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.security.Principal;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jordancho on 18.7.2017.
 */
@RestController
@RequestMapping(value = "/api/public", produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicController {

    private final BookService bookService;
    private final BookPictureService bookPictureService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final BookDetailsService bookDetailsService;
    private final Logger logger = LoggerFactory.getLogger(PublicController.class);


    public PublicController(BookService bookService,
                            BookPictureService bookPictureService,
                            BCryptPasswordEncoder bCryptPasswordEncoder,
                            UserService userService,
                            BookDetailsService bookDetailsService) {
        this.bookService = bookService;
        this.bookPictureService = bookPictureService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.bookDetailsService = bookDetailsService;
    }

    @GetMapping(value = "/books/{sort}")
    @Transactional
    public List<Book> getAllBooks(@PathVariable String sort) {
        switch(sort) {
            case "name" :
                return bookService.getAllBooks().stream().sorted(Comparator.comparing(o -> o.name)).collect(Collectors.toList());
            case "author" :
                return bookService.getAllBooks().stream().sorted(Comparator.comparing(o -> o.authors.get(0).name)).collect(Collectors.toList());
            default:
                return bookService.getAllBooks().stream().sorted(Comparator.comparing(o -> o.id)).collect(Collectors.toList());
        }

    }

    @Transactional
    @GetMapping(value = "/books/search/{searchKey}")
    public List<Book> searchBook(@PathVariable String searchKey) {
        return bookService.searchBook(searchKey);
    }


    @GetMapping(value = "/books/{id}/img")
    @Transactional
    public void getImage(
            @PathVariable Long id,
            HttpServletResponse httpServletResponse) throws SQLException, IOException {
        BookPicture bookPicture = bookService.findBook(id).bookPicture;
        if (bookPicture == null) {
            bookPicture = bookPictureService.getDefaultPicture();
        }
        httpServletResponse.setContentType(bookPicture.picture.contentType);
        httpServletResponse.setContentLength(bookPicture.picture.size);
        OutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(bookPicture.picture.data);
        outputStream.flush();
    }

    @PostMapping(value = "/user/registration")
    public User userRegistration(@RequestBody User user) {
        if (userService.findByUsername(user.username) != null) {
            logger.info("Username exist");
            throw new UsernameExistException("This username is not available");
        }
        User newUser = new User(user.username, user.password);
        newUser.email = user.email;
        newUser.name = user.name;
        newUser.lastName = user.lastName;
        newUser.password = bCryptPasswordEncoder.encode(user.password);
        newUser.gender = user.gender;
        newUser.city = user.city;
        logger.info("Trying to register a user");
        return userService.createUser(newUser);
    }

    @GetMapping("/user")
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();
        if (principal != null) {
            map.put("name", principal.getName());
        } else {
            map.put("name", "N/A");
        }
        return map;
    }

    @Transactional
    @GetMapping(value = "/{bookId}/books")
    public BookDetails getBookDetails(@PathVariable String bookId) {
        return bookDetailsService.getBookDetailsByBookId(new Long(bookId));
    }

    @ExceptionHandler(UsernameExistException.class)
    public Map<String, String> onNotFound(UsernameExistException e) {
        logger.debug("Username exist");
        Map<String, String> map = new HashMap<>();
        map.put("error", e.toString());
        return map;
    }
}
