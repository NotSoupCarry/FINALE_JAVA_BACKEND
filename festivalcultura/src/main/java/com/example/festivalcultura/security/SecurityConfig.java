package com.example.festivalcultura.security;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.festivalcultura.models.Utente;
import com.example.festivalcultura.repositoryes.UtenteRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UtenteRepository utenteRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            Utente utente = utenteRepository.findByEmail(email);
            if (utente == null) {
                throw new UsernameNotFoundException("Utente non trovato con email: " + email);
            }
            // Convertiamo il ruolo enum in una stringa compatibile con Spring Security
            List<GrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority(utente.getRuolo().name()));

            return new org.springframework.security.core.userdetails.User(
                    utente.getEmail(),
                    utente.getPassword(),
                    authorities);
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for simplicity (consider enabling it in production)
                .csrf(csrf -> csrf.disable())

                // Configure access control
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/css/**", "/js/**").permitAll() 
                        .requestMatchers("/eventi").permitAll() 
                        .requestMatchers("/admin/**").hasRole("ADMIN") 
                        .anyRequest().authenticated())

                // Configure the login form
                .formLogin(form -> form
                        .loginPage("/eventi") // The login page is the /eventi page
                        .loginProcessingUrl("/auth/login") // URL to process login form submission
                        .successHandler((request, response, authentication) -> {
                            // Redirect based on the role of the authenticated user
                            String targetUrl;
                            if (authentication.getAuthorities().stream()
                                    .anyMatch(
                                            grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                                targetUrl = "/admin/eventi"; // Redirect to /admin/eventi for ADMIN
                            } else {
                                targetUrl = "/eventi"; // Redirect to /eventi for regular users (ROLE_USER)
                            }
                            response.sendRedirect(targetUrl);
                        })
                        .permitAll()) // Allow everyone to access the login form

                // Configure logout functionality
                .logout(logout -> logout
                        .logoutUrl("/auth/logout") // URL to trigger logout
                        .logoutSuccessUrl("/eventi?logout") // Redirect to /eventi after successful logout
                        .permitAll()) // Allow everyone to access the logout URL

                // Configure authentication provider
                .authenticationProvider(authenticationProvider());

        return http.build();
    }

}
