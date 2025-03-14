package com.example.festivalcultura.controllers;

import com.example.festivalcultura.models.Evento;
import com.example.festivalcultura.models.Prenotazione;
import com.example.festivalcultura.models.Utente;
import com.example.festivalcultura.services.EventoService;
import com.example.festivalcultura.services.PrenotazioneService;
import com.example.festivalcultura.services.UtenteService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/eventi")
public class EventoController {

    private final EventoService eventoService;
    private final UtenteService utenteService;
    private final PrenotazioneService prenotazioneService;

    // Metodo per visualizzare la pagina degli eventi
    @GetMapping()
    public String getAllEventi(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Utente utenteLoggato = utenteService.findByEmail(email);

        List<Evento> eventiDisponibili;

        if (utenteLoggato != null) {
            // Ottieni tutti gli eventi
            List<Evento> tuttiEventi = eventoService.getAllEventi();

            // Ottieni tutti gli eventi prenotati dall'utente
            List<Evento> eventiPrenotati = prenotazioneService.findByUtente(utenteLoggato)
                    .stream()
                    .map(Prenotazione::getEvento) // Ottieni solo gli eventi dalle prenotazioni
                    .toList();

            // Filtra gli eventi disponibili rimuovendo quelli già prenotati
            eventiDisponibili = tuttiEventi.stream()
                    .filter(evento -> !eventiPrenotati.contains(evento))
                    .toList();
        } else {
            // Se l'utente non è loggato, mostra tutti gli eventi
            eventiDisponibili = eventoService.getAllEventi();
        }
        
        model.addAttribute("eventi", eventiDisponibili);
        return "eventi"; // Nome della pagina HTML
    }
}
