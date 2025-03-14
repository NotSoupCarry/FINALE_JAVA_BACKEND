package com.example.festivalcultura.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "eventi")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titolo;

    private String descrizione;

    private LocalDateTime dataOra; // un campo combinato per data e orario

    private double prezzo;

    @ManyToOne
    @JoinColumn(name = "sede_id")
    private Sede sede;  // Relazione Many-to-One con Sede

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prenotazione> prenotazioni;  // Relazione One-to-Many con Prenotazione

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recensione> recensioni;  // Relazione One-to-Many con Recensione

    @ManyToMany
    @JoinTable(
        name = "evento_artista", 
        joinColumns = @JoinColumn(name = "evento_id"), 
        inverseJoinColumns = @JoinColumn(name = "artista_id")
    )
    private List<Artista> artisti; // Relazione Many-to-Many con Artista
}
