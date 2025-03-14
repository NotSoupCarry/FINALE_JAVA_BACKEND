package com.example.festivalcultura.controllers;

import com.example.festivalcultura.models.Evento;
import com.example.festivalcultura.models.Recensione;
import com.example.festivalcultura.services.RecensioneService;
import com.example.festivalcultura.services.UtenteService;
import com.example.festivalcultura.services.EventoService;
import com.example.festivalcultura.services.PrenotazioneService;
import com.example.festivalcultura.models.Prenotazione;
import com.example.festivalcultura.models.Utente;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recensioni")
public class RecensioneController {

    private final RecensioneService recensioneService;
    private final PrenotazioneService prenotazioneService;
    private final EventoService eventoService;
    private final UtenteService utenteService;

    @GetMapping()
    public String mostraRecensioniUtente(Model model) {
        // Ottieni l'email dell'utente loggato
        String email = SecurityContextHolder.getContext().getAuthentication().getName();  // Recupera l'email dell'utente loggato
        Utente utenteLoggato = utenteService.findByEmail(email);  // Usa il servizio Utente per trovare l'utente

        if (utenteLoggato == null) {
            // Se non trovi l'utente, reindirizza o mostra un errore
            return "redirect:/auth/login";
        }

        // Ottieni tutte le recensioni dell'utente
        List<Recensione> recensioni = recensioneService.findByUtente(utenteLoggato);
        model.addAttribute("recensioni", recensioni);

        return "recensioni";  // Il template per visualizzare le recensioni
    }

    // Visualizza il form per aggiungere una recensione per un evento
    @GetMapping("/aggiungi/{eventoId}")
    public String mostraFormRecensione(@PathVariable Long eventoId, Model model) {
        // Verifica se l'utente ha prenotato l'evento
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); 
                                                                                         
        Utente utenteLoggato = utenteService.findByEmail(email);
        Prenotazione prenotazione = prenotazioneService.findByUtenteAndEvento(utenteLoggato, eventoId);

        if (prenotazione == null) {
            // Se l'utente non ha prenotato l'evento, reindirizza o mostra un errore
            return "redirect:/eventi"; // O una pagina di errore
        }

        Evento evento = eventoService.getEventoById(eventoId);
        Recensione recensione = new Recensione();
        model.addAttribute("evento", evento);
        model.addAttribute("recensione", recensione);

        return "aggiungiRecensione"; // La vista per il form
    }

    // Gestisce l'invio del form di recensione
    @PostMapping("/aggiungi/{eventoId}")
    public String aggiungiRecensione(@PathVariable Long eventoId, @ModelAttribute Recensione recensione) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); 
                                                                                         
        Utente utenteLoggato = utenteService.findByEmail(email);
        Evento evento = eventoService.getEventoById(eventoId);

        // Setta utente e evento nella recensione
        recensione.setUtente(utenteLoggato);
        recensione.setEvento(evento);

        // Salva la recensione
        recensioneService.salvaRecensione(recensione);

        return "redirect:/eventi"; // O una pagina di conferma
    }
}
