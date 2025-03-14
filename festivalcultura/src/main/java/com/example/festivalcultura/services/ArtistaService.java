package com.example.festivalcultura.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.festivalcultura.models.Artista;
import com.example.festivalcultura.repositoryes.ArtistaRepository;
import com.example.festivalcultura.repositoryes.EventoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtistaService {
    private final ArtistaRepository artistaRepository;
    private final EventoRepository eventoRepository;

    public List<Artista> getAllArtisti(){
        return artistaRepository.findAll();
    }

    public List<Artista> getArtistiByIds(List<Long> ids){
        return artistaRepository.findAllById(ids);
    }
    
    @Transactional
    public void salvaArtista(Artista artista){
        artistaRepository.save(artista);
    }

    @Transactional
    public String  eliminaArtistaById(Long idArtista){   
        if (eventoRepository.existsByArtistiId(idArtista)) {
            return "IMPOSSIBILE ELIMINARE QUESTO ARTISTA. ELIMINARE PRIMA TUTTI GLI EVENTI ASSOCIATI";
        }  
        artistaRepository.deleteById(idArtista);
        return null;  // La sede è stata eliminata con successo
    }
}