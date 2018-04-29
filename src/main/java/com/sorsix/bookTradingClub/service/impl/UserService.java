package com.sorsix.bookTradingClub.service.impl;

import com.sorsix.bookTradingClub.api.error.InvalidPasswordException;
import com.sorsix.bookTradingClub.api.error.UsernameExistException;
import com.sorsix.bookTradingClub.domain.Book;
import com.sorsix.bookTradingClub.domain.User;
import com.sorsix.bookTradingClub.domain.enums.Provider;
import com.sorsix.bookTradingClub.repository.BookRepository;
import com.sorsix.bookTradingClub.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by jordancho on 20.7.2017.
 */
@Component
public class UserService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookRepository bookRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            BookRepository bookRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.bookRepository = bookRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findByUsername(String username) {
        return (User) this.userRepository.findByUsername(username);
    }

    public User findById(Long id) {
        return this.userRepository.findOne(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepository.findByUsername(username) != null) {
            return (User) userRepository.findByUsername(username);
        } else {
            throw new UsernameNotFoundException(String.format("Username [%s] not found", username));
        }
    }

    public User createUser(String firstName,
                           String lastName,
                           String username,
                           String password,
                           String email,
                           String city,
                           String gender) {
        if (userRepository.findByUsername(username) != null) {
            logger.info("Username exist");
            throw new UsernameExistException("This username is not available");
        }
        User newUser = new User(username, password);
        newUser.email = email;
        newUser.name = firstName;
        newUser.lastName = lastName;
        newUser.password = bCryptPasswordEncoder.encode(password);
        newUser.gender = gender;
        newUser.city = city;
        newUser.provider = Provider.LOCAL;
        logger.info("Trying to register a user");
        return userRepository.save(newUser);
    }

    public Book addBookForTrade(Long bookId) {
        Book book = bookRepository.findOne(bookId);
        return bookRepository.save(book);
    }

    ;

    public User updateUser(
            String username,
            String firstName,
            String lastName,
            String password,
            String email,
            String city) {

        User user = (User) userRepository.findByUsername(username);
        if (password == null ||
                !bCryptPasswordEncoder.matches(password, user.password))
            throw new InvalidPasswordException("Invalid password");
        logger.info("Update for " + username);
        user.name = firstName;
        user.lastName = lastName;
        user.username = username;
        user.email = email;
        user.city = city;
        return userRepository.save(user);
    }
}
