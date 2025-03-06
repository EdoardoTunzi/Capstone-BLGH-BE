package com.example.Capstone_BLGH_BE.repository;

import com.example.Capstone_BLGH_BE.model.entities.Evento;
import com.example.Capstone_BLGH_BE.model.entities.Partecipazione;
import com.example.Capstone_BLGH_BE.model.entities.Utente;
import com.example.Capstone_BLGH_BE.model.enumerations.StatoPartecipazione;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PartecipazioneDAORepository extends JpaRepository<Partecipazione, Long> {

    public Page<Partecipazione> findByUtenteAndStatoPartecipazioneOrderByEventoData(Utente utente, StatoPartecipazione stato, Pageable page);
    boolean existsByUtenteAndEvento(Utente utente, Evento evento);

    @Query("SELECT p.evento, COUNT(p) as partecipazioni FROM Partecipazione p " +
            "WHERE p.statoPartecipazione = 'PARTECIPERO' " +
            "GROUP BY p.evento " +
            "ORDER BY partecipazioni DESC")
    public Page<Object[]> findTopEventiByPartecipazioni(Pageable pageable);
}
