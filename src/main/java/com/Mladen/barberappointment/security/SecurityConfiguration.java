package com.Mladen.barberappointment.security;

import com.Mladen.barberappointment.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {

        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/showAppointmentForm").permitAll()
                        .requestMatchers("/showRegistrationForm").permitAll()
                        .requestMatchers("/processRegistrationForm").permitAll()
                        .anyRequest().authenticated()
        ).csrf(csrf->csrf.disable()
        ).formLogin(form ->
                form
                        .loginPage("/showLoginForm")
                        .loginProcessingUrl("/authenticateTheUser")
                        .successHandler(customAuthenticationSuccessHandler)
                        .permitAll()
        ).logout(logout -> logout.permitAll())
                .exceptionHandling(exception->
                        exception.accessDeniedPage("/error"));


        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserService userService)
    {
        DaoAuthenticationProvider auth =new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder());
        auth.setUserDetailsService(userService);
        return auth;
    }
}
