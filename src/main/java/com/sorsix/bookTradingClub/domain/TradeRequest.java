package com.sorsix.bookTradingClub.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jordancho on 26.7.2017.
 */
@Entity
@Table(name = "trade_requests")
public class TradeRequest extends BaseEntity {

    public TradeRequest() {
    }

    public TradeRequest(User user, Book givenBook, Book takenBook) {
        this.user = user;
        this.givenBook = givenBook;
        this.takenBook = takenBook;
    }

    @OneToOne
    public User user;

    @OneToOne
    public Book givenBook;

    @OneToOne
    public Book takenBook;
}
