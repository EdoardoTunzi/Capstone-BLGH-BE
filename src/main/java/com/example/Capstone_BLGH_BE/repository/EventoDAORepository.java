package com.example.Capstone_BLGH_BE.repository;

import com.example.Capstone_BLGH_BE.model.entities.Band;
import com.example.Capstone_BLGH_BE.model.entities.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventoDAORepository extends JpaRepository<Evento, Long> {

    public boolean existsByNome(String nome);
    // Filtra solo eventi con data uguale a oggi o futura, in modo da escludere eventi gia avvenuti
    Page<Evento> findByDataGreaterThanEqualOrderByDataAsc(LocalDate oggi, Pageable page);

    // Filtra per data specifica
    Page<Evento> findByDataOrderByDataAsc(LocalDate data, Pageable page);

    // Filtra per nome band (con ricerca parziale)
    Page<Evento> findByBandNomeBandContainingIgnoreCaseAndDataGreaterThanEqualOrderByDataAsc(String nomeBand, LocalDate oggi, Pageable page);

    // Filtra per location (con ricerca parziale)
    Page<Evento> findByLocationContainingIgnoreCaseAndDataGreaterThanEqualOrderByDataDesc(String location, LocalDate oggi, Pageable page);}

