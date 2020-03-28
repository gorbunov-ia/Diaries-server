package ru.gorbunov.diaries.config;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;

/**
 * Configuration for Spring Security.
 *
 * @author Gorbunov.ia
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * {@link org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder}.
     */
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * Implementation of service for interaction with User Details.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Base constructor.
     *
     * @param userDetailsService           service for interaction with user details
     * @param authenticationManagerBuilder spring authentication manager builder
     */
    public WebSecurityConfig(final UserDetailsService userDetailsService,
                             final AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userDetailsService = userDetailsService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic()
            .and()
                .rememberMe()
                .rememberMeServices(rememberMeServices())
            .and()
                .authorizeRequests()
                .antMatchers("/api/**", "/logout").authenticated()
            .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
                .cors();
            //h2-console settings
//                .disable()
//            .headers()
//                .frameOptions()
//                .disable();

    }

    /**
     * Initialization.
     */
    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    /**
     * Bean for get password hash.
     *
     * @return bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configure remember me service.
     *
     * @return instance
     */
    @Bean
    public RememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices meServices = new SpringSessionRememberMeServices();
        meServices.setAlwaysRemember(true);
        return meServices;
    }

    /**
     * Configure CORS security.
     *
     * @return instance
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
