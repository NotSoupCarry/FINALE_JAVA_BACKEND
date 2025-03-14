package com.example.festivalcultura.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recensioni")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recensione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int valutazione;

    private String commento;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente; // Relazione Many-to-One con Utente

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento; // Relazione Many-to-One con Evento
}
