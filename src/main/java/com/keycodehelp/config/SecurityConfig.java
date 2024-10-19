package com.keycodehelp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Enable CSRF protection (default)
                // Session management
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )

                // Configure public access for static resources and login/signup pages
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // Allow access to static resources (CSS, JS, images)
                                .requestMatchers("/static/**", "/static", "/css/**", "/js/**", "/images/**", "user-dashboard.html", "/home.html").permitAll()

                                // Public access for login, signup, and error pages
                                .requestMatchers("/login", "/signup", "/register.html", "/error", "/auth/**").permitAll()

                                // Admin-specific access control
                                .requestMatchers("/admin/**").hasRole("ADMIN")

                                // Any other requests require authentication
                                .anyRequest().authenticated()
                )

                // Configure form login
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/user-dashboard", true)
                        .permitAll()
                        .failureUrl("/login?error")
                )

                // Configure logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                );

        // Set custom UserDetailsService
        http.userDetailsService(userDetailsService);

        return http.build();
    }

    // Authentication manager bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
