package com.sorsix.bookTradingClub.domain.dto;

import com.sorsix.bookTradingClub.domain.Author;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by jordancho on 09.8.2017.
 */
public class BookDto {

    public BookDto() {
    }

    public BookDto(String name, List<String> authors, String description){
        this.name = name;
        this.authors = authors;
        this.description = description;
    }

    @NotNull
    @Length(min = 3)
    public String name;

    public List<String> authors;

    @Length(max = 5000)
    public String description;
}
