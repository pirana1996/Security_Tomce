package com.sorsix.bookTradingClub.domain;

import javax.persistence.*;

/**
 * Created by jordancho on 18.7.2017.
 */
@Entity
@Table(name = "book_pictures")
public class BookPicture extends BaseEntity{

    @Embedded
    public FileEmbeddable picture;

    /*@OneToOne(cascade = CascadeType.REMOVE)
    public Book book;
*/
    public BookPicture() {
    }

    public BookPicture(FileEmbeddable picture) {
        this.picture = picture;
    }

}
