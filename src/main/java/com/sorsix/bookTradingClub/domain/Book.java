package com.sorsix.bookTradingClub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by jordancho on 17.7.2017.
 */
@Entity
@Table(name = "books")
public class Book extends BaseEntity{

    public Book() {
    }

    @NotNull
    public String name;

    public Book(String name) {
        this.name = name;
    }

    @ManyToOne
    public User user;

    @OneToOne(cascade = CascadeType.REMOVE)
    public BookPicture bookPicture;

    @ManyToMany
    public List<Author> authors;

    @JsonManagedReference
    @OneToOne(mappedBy = "book", cascade = CascadeType.REMOVE)
    public BookDetails bookDetails;
}
