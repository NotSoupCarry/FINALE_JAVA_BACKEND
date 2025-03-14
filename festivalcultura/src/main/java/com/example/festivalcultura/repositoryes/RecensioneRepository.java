package com.example.festivalcultura.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.festivalcultura.models.Recensione;

public interface RecensioneRepository extends JpaRepository<Recensione, Long>{
    
}
