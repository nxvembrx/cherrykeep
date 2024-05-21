package com.alxwnth.cherrybox.cherrykeep.config;

import com.alxwnth.cherrybox.cherrykeep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserService userService;
    private final Argon2PasswordEncoder argon2PasswordEncoder;

    public WebSecurityConfig(UserService userService, Argon2PasswordEncoder argon2PasswordEncoder) {
        this.userService = userService;
        this.argon2PasswordEncoder = argon2PasswordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/signup").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form.loginPage("/login").permitAll())
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    @Autowired
    protected void configureGlobals(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(argon2PasswordEncoder);
    }
}
