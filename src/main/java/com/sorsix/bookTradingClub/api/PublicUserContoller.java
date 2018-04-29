package com.sorsix.bookTradingClub.api;

import com.sorsix.bookTradingClub.api.error.UsernameExistException;
import com.sorsix.bookTradingClub.domain.User;
import com.sorsix.bookTradingClub.domain.dto.UserDto;
import com.sorsix.bookTradingClub.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jordancho on 10.8.2017.
 */
@RestController
@RequestMapping(value = "/api/public/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicUserContoller {

    private final UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    public PublicUserContoller(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/registration")
    public User userRegistration(@RequestBody User user) {
        return userService.createUser(
                user.name,
                user.lastName,
                user.username,
                user.password,
                user.email,
                user.city,
                user.gender);
    }

    @GetMapping
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();
        if (principal != null) {
            map.put("name", principal.getName());
        } else {
            map.put("name", "N/A");
        }
        return map;
    }

    @ExceptionHandler(UsernameExistException.class)
    public Map<String, String> onNotFound(UsernameExistException e) {
        logger.debug("Username exist");
        Map<String, String> map = new HashMap<>();
        map.put("error", e.toString());
        return map;
    }
}
