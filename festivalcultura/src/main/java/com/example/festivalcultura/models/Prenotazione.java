package com.example.festivalcultura.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "prenotazioni")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataPrenotazione;

    private int numeroBiglietti;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;  // Relazione Many-to-One con Utente

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;  // Relazione Many-to-One con Evento
}
