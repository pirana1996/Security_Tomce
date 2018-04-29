package com.sorsix.bookTradingClub.listeners;

import com.sorsix.bookTradingClub.domain.User;
import com.sorsix.bookTradingClub.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by jordancho on 08.8.2017.
 */
@Component
public class AuthenticationListener {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationListener.class);

    public AuthenticationListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @EventListener
    void onAuthenticationSuccess(AuthenticationSuccessEvent authenticationSuccessEvent) {
        logger.info("User authenticated");
        try {
            OAuth2Authentication authentication = (OAuth2Authentication) authenticationSuccessEvent.getAuthentication();
            User user = (User) userRepository.findByUsername(authentication.getName());
            if (user == null) {
                User newUser = new User();
                newUser.username = authentication.getName();
                Map<String, String> map = (Map<String, String>) authentication.getUserAuthentication().getDetails();
                String name = map.get("name").toString();
                OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) authentication.getDetails();

                if (name.split(" ").length > 1) {
                    newUser.name = name.split(" ")[0];
                    newUser.lastName = name.split(" ")[1];
                } else {
                    newUser.name = name;
                }
                userRepository.save(newUser);
            }
        } catch (ClassCastException ex) {
            logger.info("Can not cast => local authentication");
        }

    }
}
