package com.sorsix.bookTradingClub.domain;

import javax.persistence.*;

/**
 * Created by jordancho on 26.7.2017.
 */
@Entity
@Table(name = "trades")
public class Trade extends BaseEntity {

    public Trade() {
    }

    public Trade(User user, Book givenBook, Book takenBook) {
        this.user = user;
        this.givenBook = givenBook;
        this.takenBook = takenBook;
    }

    @OneToOne
    public User user;

    @ManyToOne
    public Book givenBook;

    @ManyToOne
    public Book takenBook;
}
