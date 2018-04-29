package com.sorsix.bookTradingClub.api;

import com.sorsix.bookTradingClub.api.error.InvalidPasswordException;
import com.sorsix.bookTradingClub.domain.*;
import com.sorsix.bookTradingClub.domain.dto.UserDto;
import com.sorsix.bookTradingClub.service.*;
import com.sorsix.bookTradingClub.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Created by jordancho on 17.7.2017.
 */
@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final BookService bookService;
    private final UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(BookService bookService,
                          UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }


    @GetMapping(value = "/books")
    public List<Book> getUserBooks(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return bookService.getUserBooks(user);
    }


    @GetMapping(value = "")
    public User getUserDetails(Principal principal) {
        return userService.findByUsername(principal.getName());
    }

    @PutMapping(value = "/update")
    public User updateUserDetails(@RequestBody @Valid UserDto userDto,
                                  Principal principal) {
        return userService.updateUser(
                principal.getName(),
                userDto.firstName,
                userDto.lastName,
                userDto.password,
                userDto.email,
                userDto.city);
    }
}
