package com.example.Capstone_BLGH_BE.repository;

import com.example.Capstone_BLGH_BE.model.entities.Band;
import com.example.Capstone_BLGH_BE.model.entities.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventoDAORepository extends JpaRepository<Evento, Long> {

    public boolean existsByNome(String nome);
    //filtra solo eventi con data uguale a oggi o futura, in modo da escludere eventi gia avvenuti
    Page<Evento> findByDataGreaterThanEqualOrderByDataAsc(LocalDate data, Pageable page);
}

