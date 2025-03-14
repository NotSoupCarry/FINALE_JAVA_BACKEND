package com.example.festivalcultura.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.festivalcultura.models.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Utente findByEmail(String email);
}
