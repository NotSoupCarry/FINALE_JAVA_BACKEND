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
                // Disabilitiamo il CSRF per semplificare (in produzione valutare attentamente
                // questa scelta)
                .csrf(csrf -> csrf.disable())
                // Configuriamo le autorizzazioni: le pagine di registrazione e login sono
                // accessibili a tutti
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Accesso solo per admin
                        .anyRequest().authenticated())
                // Configuriamo il form di login
                .formLogin(form -> form
                        .loginPage("/auth/login") // Pagina personalizzata di login
                        .loginProcessingUrl("/auth/login") // URL a cui il form invia i dati
                        .successHandler((request, response, authentication) -> {
                            // Dopo il login con successo, reindirizza alla pagina /utenti
                            response.sendRedirect("/auth/register");
                        })
                        .permitAll())
                // Configuriamo il logout
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login?logout")
                        .permitAll())
                // Configuriamo il provider di autenticazione
                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}
