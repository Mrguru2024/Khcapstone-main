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
<<<<<<< HEAD
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
=======
                // Enable CSRF protection using cookies
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())  // Use cookie-based CSRF tokens
                )
                // Configure session management
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Create session if necessary
                        .invalidSessionUrl("/login?sessionExpired=true")  // Redirect to log in on session expiration
                        .maximumSessions(100)  // Limit to one session per user
                        .sessionRegistry(sessionRegistry())  // Use SessionRegistry for tracking
                )
                // Define URL access rules
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // Permit all for static resources and certain URLs
                                .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**", "/login", "/signup", "/register", "/error", "/auth/**", "/home", "/footer", "/header", "/navbar", "/keycode", "/vin-lookup", "/about", "/contact").permitAll()                                .requestMatchers("/footer", "/header", "navbar").permitAll()
                                // Restrict access to the user dashboard and admin pages
                                .requestMatchers("/user-dashboard").hasRole("USER")
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                // Require authentication for any other requests
                                .anyRequest().anonymous()
>>>>>>> 6077084 (Added tailwind proper config and classes for basic front end use, Added Exception, Dev-prop, Prop-prop, input.css, style.css, added missing controllers, fragments folder, customized header, footer and navbar (ready to use as a component. Fixed looping issues in the path settings.))
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
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

<<<<<<< HEAD
        // Set custom UserDetailsService
=======
        // Set custom userDetailsService for authentication
>>>>>>> 6077084 (Added tailwind proper config and classes for basic front end use, Added Exception, Dev-prop, Prop-prop, input.css, style.css, added missing controllers, fragments folder, customized header, footer and navbar (ready to use as a component. Fixed looping issues in the path settings.))
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
<<<<<<< HEAD
=======

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
>>>>>>> 6077084 (Added tailwind proper config and classes for basic front end use, Added Exception, Dev-prop, Prop-prop, input.css, style.css, added missing controllers, fragments folder, customized header, footer and navbar (ready to use as a component. Fixed looping issues in the path settings.))
}
