package com.example.Capstone_BLGH_BE.repository;

import com.example.Capstone_BLGH_BE.model.entities.Band;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandDAORepository extends JpaRepository<Band, Long> {
}
