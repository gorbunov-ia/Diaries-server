package ru.gorbunov.diaries.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuration for Spring Security.
 *
 * @author Gorbunov.ia
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Logout endpoint url.
     */
    private static final String API_USER_LOGOUT = "/api/user/logout";

    /**
     * Implementation of service for interaction with User Details.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Base constructor.
     *
     * @param userDetailsService service for interaction with user details
     */
    public WebSecurityConfig(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
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
                    .antMatchers("/api/**").authenticated()
                    .and()
                .logout()
                    .logoutUrl(API_USER_LOGOUT)
                    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                    .and()
                .csrf()
                    .disable()
                .cors();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
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
        configuration.addAllowedMethod(HttpMethod.DELETE);
        configuration.addAllowedMethod(HttpMethod.PUT);
        configuration.addExposedHeader("X-Auth-Token");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Setting of session resolver.
     *
     * @return instance
     */
    @Bean
    public HeaderHttpSessionIdResolver sessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }
}
