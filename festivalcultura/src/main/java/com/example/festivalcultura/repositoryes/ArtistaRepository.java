package com.example.festivalcultura.repositoryes;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.festivalcultura.models.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    
}
