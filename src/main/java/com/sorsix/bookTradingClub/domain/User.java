package com.sorsix.bookTradingClub.domain;


import com.sorsix.bookTradingClub.domain.enums.Gender;
import com.sorsix.bookTradingClub.domain.enums.Provider;
import com.sorsix.bookTradingClub.domain.enums.UserType;
import com.sun.istack.internal.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by jordancho on 20.7.2017.
 */
@Entity
@Table(name="users")
public class User implements UserDetails  {

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public String lastName;

    public String email;

    @Column(unique = true)
    @NotNull
    public String username;

    public String password;

    public String gender;

    public String city;

    public double rating;

    @Enumerated(EnumType.STRING)
    public Provider provider;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String name,
                String lastName,
                String email,
                String username,
                String password,
                String gender,
                String city) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.city = city;
        this.rating = 0;
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
