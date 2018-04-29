package com.sorsix.bookTradingClub.service;

import com.sorsix.bookTradingClub.domain.BookPicture;

import java.sql.Blob;
import java.sql.SQLException;

/**
 * Created by jordancho on 18.7.2017.
 */
public interface BookPictureService {

    BookPicture createBookPicture(
            String contentType,
            byte data[],
            String fileName,
            int size
    ) throws SQLException;

    BookPicture getDefaultPicture();

}
