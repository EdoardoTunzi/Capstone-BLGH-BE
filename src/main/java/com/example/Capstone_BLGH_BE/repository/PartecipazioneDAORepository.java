package com.example.Capstone_BLGH_BE.repository;

import com.example.Capstone_BLGH_BE.model.entities.Partecipazione;
import jakarta.servlet.http.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartecipazioneDAORepository extends JpaRepository<Partecipazione, Long> {
}
