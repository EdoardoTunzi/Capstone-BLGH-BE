package com.example.Capstone_BLGH_BE.controller;


import com.example.Capstone_BLGH_BE.model.payload.EventoDTO;
import com.example.Capstone_BLGH_BE.model.payload.UtenteDTO;
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

@RestController
@RequestMapping("/user")
public class UtenteController {

    @Autowired
    UtenteService utenteService;

    @Autowired
    EventoService eventoService;

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

    //ritorna tutti gli eventi
    @GetMapping(value = "/eventi", produces = "application/json")
    public ResponseEntity<Page<EventoDTO>> getAllEventi(Pageable page) {
        Page<EventoDTO> eventi = eventoService.getAllEventi(page);
        return new ResponseEntity<>(eventi, HttpStatus.OK);
    }
    //trova evento da id
    @GetMapping("/evento/{id}")
    public EventoDTO getEventoById(@PathVariable long id) {
        return eventoService.findEventoById(id);
    }

    //ritorna tutti gli eventi a cui parteciperà un utente
    //ritorna tutti gli eventi a cui è interessato un utente
    //ritorna tutti eventi a cui ha partecipato( dove partecipazione = parteciperò e data < a localdate.now)
    //aggiungi/crea partecipazione utente a evento e setta partecipazione da parametri
    //cambia stato partecipazione a evento di un utente
    //
}
