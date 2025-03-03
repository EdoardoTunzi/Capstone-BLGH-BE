package com.example.Capstone_BLGH_BE.service;

import com.example.Capstone_BLGH_BE.repository.UtenteDAORepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UtenteService {
    @Autowired
    UtenteDAORepository utenteRepo;

}
