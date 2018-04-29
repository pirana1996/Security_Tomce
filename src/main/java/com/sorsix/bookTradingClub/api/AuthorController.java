package com.sorsix.bookTradingClub.api;

import com.sorsix.bookTradingClub.domain.Author;
import com.sorsix.bookTradingClub.service.AuthorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jordancho on 10.8.2017.
 */

@RestController
@RequestMapping(value = "/api/authors", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping(value = "/{name}")
    public List<Author> searchAuthor(@PathVariable String name) {
        return authorService.searchAuthor(name);
    }

}
