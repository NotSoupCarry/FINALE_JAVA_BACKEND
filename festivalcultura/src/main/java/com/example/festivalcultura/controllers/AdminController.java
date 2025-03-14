package com.example.festivalcultura.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.festivalcultura.models.Artista;
import com.example.festivalcultura.models.Evento;
import com.example.festivalcultura.models.Prenotazione;
import com.example.festivalcultura.models.Recensione;
import com.example.festivalcultura.models.Sede;
import com.example.festivalcultura.services.ArtistaService;
import com.example.festivalcultura.services.EventoService;
import com.example.festivalcultura.services.PrenotazioneService;
import com.example.festivalcultura.services.RecensioneService;
import com.example.festivalcultura.services.SedeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final EventoService eventoService;
    private final SedeService sedeService;
    private final ArtistaService artistaService;
    private final PrenotazioneService prenotazioneService;
    private final RecensioneService recensioneService;

    // #region EVENTI

    // Mostra tutti gli eventi nell'area admin
    @GetMapping("/eventi")
    public String mostraEventiAdmin(Model model) {
        model.addAttribute("eventi", eventoService.getAllEventi());
        return "admin/eventi"; // Nome del template Thymeleaf per la pagina admin
    }

    // Mostra il form per aggiungere un nuovo evento
    @GetMapping("/eventi/nuovo")
    public String mostraFormNuovoEvento(Model model) {
        List<Sede> sedi = sedeService.getAllSedi();
        List<Artista> artisti = artistaService.getAllArtisti();

        model.addAttribute("evento", new Evento());
        model.addAttribute("sedi", sedi);
        model.addAttribute("artisti", artisti);
        return "admin/nuovoEvento";
    }

    // Salva il nuovo evento con sede e artisti selezionati
    @PostMapping("/eventi")
    public String salvaEvento(@ModelAttribute Evento evento,
            @RequestParam("sedeId") Long sedeId,
            @RequestParam("artistiIds") List<Long> artistiIds) {
        // Associa la sede selezionata
        Sede sede = sedeService.getSedeById(sedeId);
        evento.setSede(sede);

        // Associa gli artisti selezionati
        List<Artista> artistiSelezionati = artistaService.getArtistiByIds(artistiIds);
        evento.setArtisti(artistiSelezionati);

        eventoService.salvaEvento(evento);
        return "redirect:/admin/eventi";
    }

    @PostMapping("/eventi/elimina/{id}")
    public String eliminaEvento(@PathVariable Long id) {
        try {
            eventoService.eliminaEvento(id);
            return "redirect:/admin/eventi"; // Redirect to the events list after deletion
        } catch (Exception e) {
            // Handle exception (e.g., event has associated bookings, etc.)
            return "error"; // Show an error page (you can customize this page)
        }
    }

    // #endregion
   
    // #region SEDI

    @GetMapping("/sedi")
    public String mostraSedi(Model model) {
        List<Sede> sedi = sedeService.getAllSedi();
        model.addAttribute("sedi", sedi);
        return "admin/sedi"; // il nome del template Thymeleaf
    }

    // Aggiungere una nuova sede
    @PostMapping("/sedi")
    public String aggiungiSede(@RequestParam String nome, @RequestParam String indirizzo) {
        Sede sede = new Sede();
        sede.setNome(nome);
        sede.setIndirizzo(indirizzo);
        sedeService.aggiungiSede(sede);
        return "redirect:/admin/sedi";
    }

    // Eliminare una sede
    @PostMapping("/sedi/elimina/{id}")
    public String eliminaSede(@PathVariable Long id, Model model) {
        String errore = sedeService.eliminaSedeById(id);
        if (errore != null) {
            // Se c'è un errore, aggiungi il messaggio di errore al modello
            model.addAttribute("errore", errore);
        }
        // Visualizza di nuovo la pagina delle sedi
        return "redirect:/admin/sedi";
    }

    // #endregion

    // #region ARTISTI

    // Visualizzare tutti gli artisti
    @GetMapping("/artisti")
    public String mostraArtisti(Model model) {
        List<Artista> artisti = artistaService.getAllArtisti();
        model.addAttribute("artisti", artisti);
        model.addAttribute("errore", ""); // Aggiungi un attributo per l'errore, se necessario
        return "admin/artisti";
    }

    // Aggiungere un nuovo artista
    @PostMapping("/artisti")
    public String aggiungiArtista(@RequestParam String nome, @RequestParam String genere) {
        Artista artista = new Artista();
        artista.setNome(nome);
        artista.setGenere(genere);
        artistaService.salvaArtista(artista);
        return "redirect:/admin/artisti";
    }

    // Eliminare un artista
    @PostMapping("/artisti/elimina/{id}")
    public String eliminaArtista(@PathVariable Long id, Model model) {
        String errore = artistaService.eliminaArtistaById(id);
        if (errore != null) {
            model.addAttribute("errore", errore);
        }
        return "redirect:/admin/artisti";
    }

    // #endregion

    // #region PRENOTAZIONI

    // Visualizzare tutte le prenotazioni
    @GetMapping("/prenotazioni")
    public String mostraPrenotazioni(Model model) {
        List<Prenotazione> prenotazioni = prenotazioneService.getAll();
        model.addAttribute("prenotazioni", prenotazioni);
        model.addAttribute("errore", ""); // Aggiungi un attributo per l'errore, se necessario
        return "admin/prenotazioni";
    }

    // #endregion

    // #region RECENSIONI

    // Visualizzare tutte le recensioni
    @GetMapping("/recensioni")
    public String mostraRecensioni(Model model) {
        List<Recensione> recensioni = recensioneService.getAll();
        model.addAttribute("recensioni", recensioni);
        model.addAttribute("errore", ""); // Aggiungi un attributo per l'errore, se necessario
        return "admin/recensioni";
    }

    // #endregion
}
