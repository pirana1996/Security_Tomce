package com.sorsix.bookTradingClub.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jordancho on 20.7.2017.
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
@Import({
        AuthenticationHandlers.class
})
public class SessionSecurityConfiguration extends WebSecurityConfigurerAdapter {

    static final Logger logger = LoggerFactory.getLogger(SessionSecurityConfiguration.class);

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final AuthenticationSuccessHandler successHandler;

    private final AuthenticationFailureHandler failureHandler;

    private final LogoutSuccessHandler logoutSuccessHandler;

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final OAuth2ClientContext oauth2ClientContext;

    @Autowired
    public SessionSecurityConfiguration(
            AuthenticationEntryPoint authenticationEntryPoint,
            AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler,
            LogoutSuccessHandler logoutSuccessHandler,
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService,
            ApplicationEventPublisher applicationEventPublisher,
            OAuth2ClientContext oauth2ClientContext) {
        this.userDetailsService = userDetailsService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.oauth2ClientContext = oauth2ClientContext;
        logger.debug("Configuring Spring Session Security Configuration");
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.debug("Configuring HttpSecurity");
        // @formatter:off
        http.csrf().disable()
                .httpBasic().disable().addFilterAfter(ssoFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);


        http.formLogin()
                .loginProcessingUrl("/api/login")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .headers()
                .frameOptions()
                .disable();


        http.authorizeRequests()
                .antMatchers("/api/public/**//**")
                .permitAll();

        http.authorizeRequests()
                .antMatchers("/api/books")
                .authenticated();
    }

    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();

        OAuth2ClientAuthenticationProcessingFilter githubFilter = new OAuth2ClientAuthenticationProcessingFilter("/api/public/login/github");
        OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(github(), oauth2ClientContext);
        githubFilter.setRestTemplate(githubTemplate);
        githubFilter.setApplicationEventPublisher(applicationEventPublisher);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(githubResource().getUserInfoUri(), github().getClientId());
        tokenServices.setRestTemplate(githubTemplate);
        githubFilter.setTokenServices(tokenServices);
        filters.add(githubFilter);

        OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/api/public/login/facebook");
        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
        facebookFilter.setRestTemplate(facebookTemplate);
        tokenServices = new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId());
        tokenServices.setRestTemplate(facebookTemplate);
        facebookFilter.setApplicationEventPublisher(applicationEventPublisher);
        facebookFilter.setTokenServices(tokenServices);
        filters.add(facebookFilter);
        filter.setFilters(filters);
        return filter;
    }

    @Bean
    @ConfigurationProperties("github.client")
    public AuthorizationCodeResourceDetails github() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("github.resource")
    public ResourceServerProperties githubResource() {
        return new ResourceServerProperties();
    }

    @Bean
    @ConfigurationProperties("facebook.resource")
    public ResourceServerProperties facebookResource() {
        return new ResourceServerProperties();
    }

    @Bean
    @ConfigurationProperties("facebook.client")
    public AuthorizationCodeResourceDetails facebook() {
        return new AuthorizationCodeResourceDetails();
    }


    @Bean
        public FilterRegistrationBean oauth2ClientFilterRegistration(
                OAuth2ClientContextFilter filter) {
            FilterRegistrationBean registration = new FilterRegistrationBean();
            registration.setFilter(filter);
            registration.setOrder(-100);
            return registration;
        }
}