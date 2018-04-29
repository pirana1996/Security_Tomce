package com.sorsix.bookTradingClub.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.annotation.TypeAlias;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jordancho on 18.7.2017.
 */
@Entity
@Table(name = "book_details")
public class BookDetails extends BaseEntity{

    public BookDetails(String description, Book book) {
        this.description = description;
        this.book = book;
    }

    public BookDetails() {
    }

    @JsonBackReference
    @OneToOne
    public Book book;

    @Column(length = 5000)
    public String description;

}
