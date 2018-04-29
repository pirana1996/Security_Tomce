package com.sorsix.bookTradingClub.api;

import com.sorsix.bookTradingClub.domain.Book;
import com.sorsix.bookTradingClub.domain.BookPicture;
import com.sorsix.bookTradingClub.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by jordancho on 18.7.2017.
 */
@RestController
@RequestMapping(value = "/api/public/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicBookController {

    private final BookService bookService;
    private Logger logger = LoggerFactory.getLogger(PublicBookController.class);

    public PublicBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/{sort}")
    public List<Book> getAllBooks(@PathVariable String sort) {
        return bookService.getAllBooks(sort);

    }

    @GetMapping(value = "/search/{searchKey}")
    public List<Book> searchBook(@PathVariable String searchKey) {
        return bookService.searchBook(searchKey);
    }

    @GetMapping(value = "/{id}/img")
    @Transactional
    public void getImage(
            @PathVariable Long id,
            HttpServletResponse httpServletResponse) throws SQLException, IOException {
        BookPicture bookPicture = bookService.findBook(id).bookPicture;
        if (bookPicture == null) {
            File file = new File("./src/main/resources/images/noImageAvaliable.png");
            FileInputStream fileInputStream = new FileInputStream(file);
            int byteLength = (int) file.length();
            byte[] filecontent = new byte[byteLength];
            fileInputStream.read(filecontent, 0, byteLength);
            httpServletResponse.setContentType("png");
            httpServletResponse.setContentLength(byteLength);
            OutputStream outputStream = httpServletResponse.getOutputStream();
            outputStream.write(filecontent);
            outputStream.flush();
        } else {
            httpServletResponse.setContentType(bookPicture.picture.contentType);
            httpServletResponse.setContentLength(bookPicture.picture.size);
            OutputStream outputStream = httpServletResponse.getOutputStream();
            outputStream.write(bookPicture.picture.data);
            outputStream.flush();
        }
    }

    @GetMapping(value = "/{bookId}/details")
    public Book getBook(@PathVariable Long bookId) {
        return bookService.findBook(bookId);
    }

}
