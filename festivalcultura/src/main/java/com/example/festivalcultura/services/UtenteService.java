package com.example.festivalcultura.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.festivalcultura.enums.Ruolo;
import com.example.festivalcultura.models.Utente;
import com.example.festivalcultura.repositoryes.UtenteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtenteService {
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public Utente register(Utente utente) {
        if (utente.getRuolo() == null || utente.getRuolo().name() == null) {
            utente.setRuolo(Ruolo.ROLE_USER);
        }
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        return utenteRepository.save(utente);
    }
}
