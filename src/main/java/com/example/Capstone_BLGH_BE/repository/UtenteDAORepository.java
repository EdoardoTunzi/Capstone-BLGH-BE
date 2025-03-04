package com.example.Capstone_BLGH_BE.repository;

import com.example.Capstone_BLGH_BE.model.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteDAORepository extends JpaRepository<Utente, Long> {
    public Optional<Utente> findByUsername(String username);
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
}
