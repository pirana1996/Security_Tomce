package com.sorsix.bookTradingClub.domain;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jordancho on 30.7.2017.
 */
@Entity
@Table(name = "authors")
public class Author extends BaseEntity{

    public String name;

    public Author(String name) {
        this.name = name;
    }

    public Author() {
    }

}
