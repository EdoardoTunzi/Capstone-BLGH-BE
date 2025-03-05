package com.example.Capstone_BLGH_BE.repository;

import com.example.Capstone_BLGH_BE.model.entities.Evento;
import com.example.Capstone_BLGH_BE.model.entities.Partecipazione;
import com.example.Capstone_BLGH_BE.model.entities.Utente;
import com.example.Capstone_BLGH_BE.model.enumerations.StatoPartecipazione;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartecipazioneDAORepository extends JpaRepository<Partecipazione, Long> {

    public Page<Partecipazione> findByUtenteAndStatoPartecipazioneOrderByEventoData(Utente utente, StatoPartecipazione stato, Pageable page);
    boolean existsByUtenteAndEvento(Utente utente, Evento evento);
}
