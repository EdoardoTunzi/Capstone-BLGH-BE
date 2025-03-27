package com.example.Capstone_BLGH_BE.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Capstone_BLGH_BE.model.payload.BandDTO;
import com.example.Capstone_BLGH_BE.model.payload.UtenteDTO;
import com.example.Capstone_BLGH_BE.model.payload.request.EventoDTORequest;
import com.example.Capstone_BLGH_BE.service.BandService;
import com.example.Capstone_BLGH_BE.service.EventoService;
import com.example.Capstone_BLGH_BE.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UtenteService utenteService;

    @Autowired
    EventoService eventoService;

    @Autowired
    BandService bandService;

    @Autowired
    Cloudinary cloudinaryConfig;

    //-----------------------------GESTIONE EVENTI-------------------------------
    //Crea nuovo evento
    @PostMapping("/evento")
    public ResponseEntity<?> createNewEvento(@Validated @RequestBody EventoDTORequest dto, BindingResult validazione) {
        if (validazione.hasErrors()) {
            StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");
            for (ObjectError errore : validazione.getAllErrors()) {
                errori.append(errore.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
        }
        String messaggio = eventoService.createEvento(dto);
        return new ResponseEntity<>(messaggio, HttpStatus.OK);
    }

    //Modifica evento
    @PutMapping("/evento/{idEvento}")
    public ResponseEntity<?> updateEventoById(@Validated @RequestBody EventoDTORequest dto, @PathVariable Long idEvento, BindingResult validazione) {
        if (validazione.hasErrors()) {
            StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");
            for (ObjectError errore : validazione.getAllErrors()) {
                errori.append(errore.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
        }
        String messaggio = eventoService.updateEvento(dto, idEvento);
        return new ResponseEntity<>(messaggio, HttpStatus.OK);
    }

    //Cancella evento
    @DeleteMapping("/evento/{idEvento}")
    public ResponseEntity<?> deleteEventoById(@PathVariable Long idEvento) {
        String messaggio = eventoService.deleteEvento(idEvento);
        return new ResponseEntity<>(messaggio, HttpStatus.OK);
    }


    //-----------------------------GESTIONE BAND---------------------------------
    //Crea nuova band
    @PostMapping("/band")
    public ResponseEntity<?> createNewBand(@Validated @RequestBody BandDTO bandDTO, BindingResult validazione) {
        if (validazione.hasErrors()) {
            StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");
            for (ObjectError errore : validazione.getAllErrors()) {
                errori.append(errore.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
        }
        String messaggio = bandService.createBand(bandDTO);
        return new ResponseEntity<>(messaggio, HttpStatus.OK);
    }

    //Modifica Band
    @PutMapping("/band/{idBand}")
    public ResponseEntity<?> updateBandById(@Validated @RequestBody BandDTO dto, @PathVariable Long idBand, BindingResult validazione) {
        if (validazione.hasErrors()) {
            StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");
            for (ObjectError errore : validazione.getAllErrors()) {
                errori.append(errore.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
        }
        String messaggio = bandService.updateBand(dto, idBand);
        return new ResponseEntity<>(messaggio, HttpStatus.OK);
    }

    //Modifica foto band da Id
    @PutMapping("/band/foto/{idBand}")
    public ResponseEntity<?> updateFotoBandById(@RequestPart("foto")MultipartFile foto, @PathVariable Long idBand) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Map mappa = cloudinaryConfig.uploader().upload(foto.getBytes(), ObjectUtils.emptyMap());
            String urlImage = mappa.get("secure_url").toString();
            String message = bandService.updateFotoBandById(idBand, urlImage);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException("Errore nel caricamento dell'immagine. " + e );
        }
    }

    //Cancella band da id
    @DeleteMapping("/band/{idBand}")
    public ResponseEntity<?> deleteBandById(@PathVariable Long idBand) {
        String messaggio = bandService.deleteBandById(idBand);
        return new ResponseEntity<>(messaggio, HttpStatus.OK);
    }

    //-----------------------------GESTIONE UTENTI---------------------------------
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
