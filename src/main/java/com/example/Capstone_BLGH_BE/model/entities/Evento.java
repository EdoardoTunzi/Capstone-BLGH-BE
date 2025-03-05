package com.example.Capstone_BLGH_BE.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "eventi")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private LocalDate data;
    @Column(nullable = false)
    private LocalTime ora;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String descrizione;
    @Column(nullable = false)
    private String locandina;
    @Column(nullable = false)
    private String prezzoIngresso;
    private String urlEvento;
    @ManyToOne
    @JoinColumn(name = "band_id")
    private Band band;
}

