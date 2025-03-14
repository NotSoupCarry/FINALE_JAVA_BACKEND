package com.example.festivalcultura.services;

import com.example.festivalcultura.models.Evento;
import com.example.festivalcultura.repositoryes.EventoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    // Metodo per ottenere tutti gli eventi
    public List<Evento> getAllEventi() {
        return eventoRepository.findAll();
    }

    public Evento getEventoById(Long eventoId) {
        return eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento non trovato con ID: " + eventoId));
    }

    @Transactional
    public void salvaEvento(Evento evento) {
        eventoRepository.save(evento);
    }

    @Transactional
    public void eliminaEvento(Long eventoId) {
        // Verifica se l'evento esiste
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento non trovato con ID: " + eventoId));
        
        eventoRepository.delete(evento);
    }

}
