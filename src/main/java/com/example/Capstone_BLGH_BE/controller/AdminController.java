package com.example.Capstone_BLGH_BE.controller;

import com.example.Capstone_BLGH_BE.model.payload.EventoDTO;
import com.example.Capstone_BLGH_BE.model.payload.UtenteDTO;
import com.example.Capstone_BLGH_BE.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UtenteService utenteService;

    //crea, modifica, cancella evento

    //Ottiene lista di tutti gli utenti
    @GetMapping(value = "/utenti", produces = "application/json")
    public ResponseEntity<Page<UtenteDTO>> getAllUtenti(Pageable page) {
        Page<UtenteDTO> utenti = utenteService.getAllUtenti(page);
        return new ResponseEntity<>(utenti, HttpStatus.OK);
    }
    //Cancella un utente
    @DeleteMapping("/utente/{idUtente}")
    public ResponseEntity<?> deleteUtenteById(@PathVariable Long idUtente) {
        String messaggio = utenteService.deleteUtenteById(idUtente);
        return new ResponseEntity<>(messaggio, HttpStatus.OK);
    }

}
