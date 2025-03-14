package com.example.festivalcultura.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.festivalcultura.models.Prenotazione;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    
}
