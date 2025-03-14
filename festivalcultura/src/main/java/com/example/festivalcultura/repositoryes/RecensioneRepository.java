package com.example.festivalcultura.repositoryes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.festivalcultura.models.Recensione;
import com.example.festivalcultura.models.Utente;

public interface RecensioneRepository extends JpaRepository<Recensione, Long>{
    List<Recensione> findByUtente(Utente utente);
}
