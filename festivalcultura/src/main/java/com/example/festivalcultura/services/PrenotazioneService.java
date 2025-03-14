package com.example.festivalcultura.services;

import com.example.festivalcultura.models.Evento;
import com.example.festivalcultura.models.Prenotazione;
import com.example.festivalcultura.models.Utente;
import com.example.festivalcultura.repositoryes.PrenotazioneRepository;
import com.example.festivalcultura.repositoryes.UtenteRepository;

import jakarta.transaction.Transactional;

import com.example.festivalcultura.repositoryes.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final UtenteRepository utenteRepository;
    private final EventoRepository eventoRepository;

    public List<Prenotazione> getAll(){
        return prenotazioneRepository.findAll();
    }
    @Transactional
    public Prenotazione prenotaEvento(Long eventoId, Long utenteId, int numeroBiglietti) {
        // Recupera l'utente e l'evento dal database
        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        // Crea una nuova prenotazione
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(utente);
        prenotazione.setEvento(evento);
        prenotazione.setNumeroBiglietti(numeroBiglietti);
        prenotazione.setDataPrenotazione(LocalDateTime.now()); // Imposta la data di prenotazione come la data attuale

        // Salva la prenotazione
        return prenotazioneRepository.save(prenotazione);
    }

    // Metodo per ottenere tutte le prenotazioni dell'utente
    public List<Prenotazione> findByUtente(Utente utente) {
        return prenotazioneRepository.findByUtente(utente);
    }

    public Prenotazione findByUtenteAndEvento(Utente utente, Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento non trovato con ID: " + eventoId));
        // Trova la prenotazione per l'utente e l'evento
        return prenotazioneRepository.findByUtenteAndEvento(utente, evento);
    }
}
