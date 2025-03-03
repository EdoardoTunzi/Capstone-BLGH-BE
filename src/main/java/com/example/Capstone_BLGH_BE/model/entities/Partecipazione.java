package com.example.Capstone_BLGH_BE.model.entities;

import com.example.Capstone_BLGH_BE.model.enumerations.StatoPartecipazione;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partecipazioni")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Partecipazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;
    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;
    @Enumerated(EnumType.STRING)
    private StatoPartecipazione statoPartecipazione;
}
