package com.example.festivalcultura.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.festivalcultura.models.Sede;
import com.example.festivalcultura.repositoryes.EventoRepository;
import com.example.festivalcultura.repositoryes.SedeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SedeService {
    private final SedeRepository sedeRepository; 
    private final EventoRepository eventoRepository; 

    public List<Sede> getAllSedi(){
        return sedeRepository.findAll();
    }

    public Sede getSedeById(Long sedeId) {
        return sedeRepository.findById(sedeId)
                .orElseThrow(() -> new RuntimeException("Sede non trovata con ID: " + sedeId));
    }

    @Transactional
    public void aggiungiSede(Sede sede){
        sedeRepository.save(sede);
    }

    @Transactional
    public String  eliminaSedeById(Long idSede){
        if (eventoRepository.existsBySedeId(idSede)) {
            return "IMPOSSIBILE ELIMINARE QUESTA SEDE. ELIMINARE PRIMA TUTTI GLI EVENTI ASSOCIATI";
        }
        // Se non ci sono eventi associati, elimina la sede
        sedeRepository.deleteById(idSede);
        return null;  // La sede è stata eliminata con successo
    }
    
    
}
