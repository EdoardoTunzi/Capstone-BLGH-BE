package com.example.Capstone_BLGH_BE.repository;

import com.example.Capstone_BLGH_BE.model.entities.Band;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BandDAORepository extends JpaRepository<Band, Long> {
    //Trova band che contengono il nome specificato (parziale o completo), ignorando maiuscole e minuscole.
    List<Band> findByNomeBandContainingIgnoreCase(String nome);
}
