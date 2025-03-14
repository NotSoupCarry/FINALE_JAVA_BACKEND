package com.example.festivalcultura.services;

import com.example.festivalcultura.models.Recensione;
import com.example.festivalcultura.models.Utente;
import com.example.festivalcultura.repositoryes.RecensioneRepository;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecensioneService {

    private final RecensioneRepository recensioneRepository;

    public List<Recensione> getAll(){
        return recensioneRepository.findAll();
    }
    // Metodo per salvare la recensione
    @Transactional
    public void salvaRecensione(Recensione recensione) {
        recensioneRepository.save(recensione);
    }

    public List<Recensione> findByUtente(Utente utente) {
        return recensioneRepository.findByUtente(utente);
    }
}
