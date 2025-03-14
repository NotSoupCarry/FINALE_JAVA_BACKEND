package com.example.festivalcultura.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "artisti")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String genere; 

    @ManyToMany(mappedBy = "artisti")
    private List<Evento> eventi; // Relazione Many-to-Many con Evento
}

