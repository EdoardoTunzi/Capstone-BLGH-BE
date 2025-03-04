package com.example.Capstone_BLGH_BE.controller;

import com.example.Capstone_BLGH_BE.model.payload.EventoDTO;
import com.example.Capstone_BLGH_BE.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    @Autowired
    EventoService eventoService;

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
}
