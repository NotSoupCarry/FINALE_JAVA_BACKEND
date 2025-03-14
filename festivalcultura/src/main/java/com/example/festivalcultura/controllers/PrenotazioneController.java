package com.example.festivalcultura.controllers;

import com.example.festivalcultura.models.Utente;
import com.example.festivalcultura.services.PrenotazioneService;
import com.example.festivalcultura.services.UtenteService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;
    private final UtenteService utenteService;

    // Metodo per prenotare un evento
    @PostMapping("/{eventoId}")
    public String prenotaEvento(@PathVariable Long eventoId,
            @AuthenticationPrincipal User user,
            @RequestParam("numeroBiglietti") int numeroBiglietti) {
        // Recupera l'utente loggato tramite il nome utente (email)

        Utente utenteLoggato = utenteService.findByEmail(user.getUsername());
        Long utenteId = utenteLoggato.getId();
        // Chiama il servizio per prenotare l'evento
        prenotazioneService.prenotaEvento(eventoId, utenteId, numeroBiglietti);

        // Redirect alla pagina degli eventi con una conferma della prenotazione
        return "redirect:/eventi?prenotazioneSuccesso=true";
    }

    @GetMapping()
    public String getPrenotazioni(Model model) {
        // Recuperiamo l'utente loggato
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Utente utente = utenteService.findByEmail(email);

        // Otteniamo tutte le prenotazioni dell'utente
        model.addAttribute("prenotazioni", prenotazioneService.findByUtente(utente));

        return "prenotazioni"; // Pagina che mostra tutte le prenotazioni dell'utente
    }
}
