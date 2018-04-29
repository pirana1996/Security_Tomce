package com.sorsix.bookTradingClub.repository;

import com.sorsix.bookTradingClub.domain.Book;
import com.sorsix.bookTradingClub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jordancho on 20.7.2017.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByUsername(String username);

}
