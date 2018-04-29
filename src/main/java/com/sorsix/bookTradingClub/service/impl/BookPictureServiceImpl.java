package com.sorsix.bookTradingClub.service.impl;

import com.sorsix.bookTradingClub.domain.BookPicture;
import com.sorsix.bookTradingClub.domain.FileEmbeddable;
import com.sorsix.bookTradingClub.repository.BookPictureRepository;
import com.sorsix.bookTradingClub.service.BookPictureService;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Created by jordancho on 18.7.2017.
 */
@Service
public class BookPictureServiceImpl implements BookPictureService{

    private final BookPictureRepository bookPictureRepository;

    public BookPictureServiceImpl(BookPictureRepository bookPictureRepository) {
        this.bookPictureRepository = bookPictureRepository;
    }

    @Override
    public BookPicture createBookPicture(String contentType, byte data[], String fileName, int size) throws SQLException {
        FileEmbeddable fileEmbeddable = new FileEmbeddable(data, fileName, contentType, size);
        BookPicture bookPicture = new BookPicture(fileEmbeddable);
        return  bookPictureRepository.save(bookPicture);
    }

    @Override
    public BookPicture getDefaultPicture() {
        return bookPictureRepository.findOne(0l);
    }

}
