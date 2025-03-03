package com.example.Capstone_BLGH_BE.repository;

import com.example.Capstone_BLGH_BE.model.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteDAORepository extends JpaRepository<Utente, Long> {
}
