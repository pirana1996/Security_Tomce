package com.sorsix.bookTradingClub.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jordancho on 20.7.2017.
 */
@Configuration
public class AuthenticationHandlers {

    static final Logger logger = LoggerFactory.getLogger(AuthenticationHandlers.class);

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            /**
             * Always returns a 401 error code to the client.
             */
            @Override
            public void commence(HttpServletRequest request,
                                 HttpServletResponse response,
                                 AuthenticationException authenticationException) throws IOException,
                    ServletException {
                logger.debug("Pre-authenticated entry point called. Rejecting access");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
            }
        };
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            logger.debug("Authentication success");
            response.setStatus(HttpStatus.OK.value());
        };
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return (request, response, exception) -> {
            logger.debug("Authentication failure");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) ->
                response.setStatus(HttpStatus.OK.value());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}