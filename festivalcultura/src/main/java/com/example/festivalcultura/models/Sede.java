package com.example.festivalcultura.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "sedi")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String indirizzo;

    private int capienza;

    @OneToMany(mappedBy = "sede")
    private List<Evento> eventi;  // Una sede può ospitare più eventi
}
