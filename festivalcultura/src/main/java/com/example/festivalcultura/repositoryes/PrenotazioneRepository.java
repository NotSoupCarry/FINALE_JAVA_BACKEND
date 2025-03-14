package com.example.festivalcultura.repositoryes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.festivalcultura.models.Evento;
import com.example.festivalcultura.models.Prenotazione;
import com.example.festivalcultura.models.Utente;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByUtente(Utente utente);
    Prenotazione findByUtenteAndEvento(Utente utente, Evento evento);
}
