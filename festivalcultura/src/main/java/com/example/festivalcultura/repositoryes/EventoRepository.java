package com.example.festivalcultura.repositoryes;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.festivalcultura.models.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    boolean existsBySedeId(Long sedeId);
}
