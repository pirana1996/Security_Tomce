package com.sorsix.bookTradingClub.domain.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * Created by jordancho on 10.8.2017.
 */
public class UserDto {

    @Length(min = 3, message = "First name not valid")
    @NotNull(message = "First name can not be null")
    public String firstName;

    @Length(min = 3, message = "Last name not valid")
    @NotNull(message = "Last name can not be null")
    public String lastName;

    @NotNull(message = "Email can not be null")
    @Email(message = "Email not valid")
    public String email;
    public String city;

    public String password;

    public UserDto(String firstName, String lastName, String email, String city, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.password = password;
    }

    public UserDto() {
    }


}
