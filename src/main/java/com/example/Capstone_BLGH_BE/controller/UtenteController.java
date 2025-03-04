package com.example.Capstone_BLGH_BE.controller;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Capstone_BLGH_BE.model.payload.EventoDTO;
import com.example.Capstone_BLGH_BE.model.payload.UtenteDTO;
import com.example.Capstone_BLGH_BE.model.payload.request.PasswordChangeRequest;
import com.example.Capstone_BLGH_BE.service.EventoService;
import com.example.Capstone_BLGH_BE.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UtenteController {

    @Autowired
    UtenteService utenteService;

    @Autowired
    EventoService eventoService;

    @Autowired
    Cloudinary cloudinaryConfig;

    //ritorna i dati dell'utente loggato
    @GetMapping("/me")
    public ResponseEntity<UtenteDTO> getLoggedUtenteInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UtenteDTO utenteDTO = utenteService.getUtenteByUsername(username);
        return new ResponseEntity<>(utenteDTO, HttpStatus.OK);
    }
    //modifica i dati dell'utente loggato(escluso modifica password)
    @PutMapping("/me")
    public ResponseEntity<UtenteDTO> updateLoggedUtenteInfo(@RequestBody UtenteDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UtenteDTO utenteDTO = utenteService.updateUtenteInfoByUsername(username, dto);
        return new ResponseEntity<>(utenteDTO, HttpStatus.OK);
    }
    //modifica password utente loggato
    @PutMapping("/me/password")
    public ResponseEntity<?> updateLoggedUtentePassword(@RequestBody PasswordChangeRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String messaggio = utenteService.updatePasswordUtenteByUsername(username, request.getOldPassword(), request.getNewPassword());
        return new ResponseEntity<>(messaggio, HttpStatus.OK);
    }

    @PutMapping("/me/avatar")
    public ResponseEntity<?> updateLoggedUtenteAvatar(@RequestPart("avatar")MultipartFile avatar) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Map mappa = cloudinaryConfig.uploader().upload(avatar.getBytes(), ObjectUtils.emptyMap());
            String urlImage = mappa.get("secure_url").toString();
            utenteService.updateAvatarPicByUsername(username, urlImage);
            return new ResponseEntity<>("Immagine avatar sostituita", HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException("Errore nel caricamento dell'immagine. " + e );
        }
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteLoggedUtente() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String messaggio = utenteService.deleteLoggedUtente(username);
        return new ResponseEntity<>(messaggio, HttpStatus.OK);
    }



    //ritorna tutti gli eventi a cui parteciperà un utente
    //ritorna tutti gli eventi a cui è interessato un utente
    //ritorna tutti eventi a cui ha partecipato( dove partecipazione = parteciperò e data < a localdate.now)
    //aggiungi/crea partecipazione utente a evento e setta partecipazione da parametri
    //cambia stato partecipazione a evento di un utente
    //
}
