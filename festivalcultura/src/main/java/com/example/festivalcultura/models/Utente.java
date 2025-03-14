package com.example.festivalcultura.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.example.festivalcultura.enums.Ruolo;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;  // Enum per USER e ADMIN

    @OneToMany(mappedBy = "utente")
    private List<Prenotazione> prenotazioni;

    @OneToMany(mappedBy = "utente")
    private List<Recensione> recensioni;
}
