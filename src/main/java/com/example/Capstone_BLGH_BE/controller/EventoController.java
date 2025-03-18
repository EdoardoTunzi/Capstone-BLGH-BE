package com.example.Capstone_BLGH_BE.controller;

import com.example.Capstone_BLGH_BE.model.payload.EventoDTO;
import com.example.Capstone_BLGH_BE.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    @Autowired
    EventoService eventoService;

    //Ritorna tutti gli eventi - paginati, esclude quelli passati e li ordina dal piu recente
    @GetMapping("")
    public ResponseEntity<Page<EventoDTO>> getAllEventi(@PageableDefault(size = 500) Pageable page) {
        Page<EventoDTO> eventi = eventoService.getAllEventi(page);
        return new ResponseEntity<>(eventi, HttpStatus.OK);
    }
    //Trova evento da id
    @GetMapping("/{id}")
    public EventoDTO getEventoById(@PathVariable long id) {
        return eventoService.findEventoById(id);
    }

    //Filtra eventi per data specifica
    // es. /eventi/data?data=2025-06-12
    @GetMapping("/data")
    public ResponseEntity<Page<EventoDTO>> getEventiByData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,@PageableDefault(size = 500) Pageable pageable) {
        Page<EventoDTO> lista = eventoService.getEventiByData(data, pageable);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    //Filtra eventi per nome band, anche parziale, esclude eventi passati e ordina per data evento ASC
    // es. /eventi/band?nomeBand=rock
    @GetMapping("/band")
    public ResponseEntity<Page<EventoDTO>> getEventiByBand(
            @RequestParam String nomeBand,@PageableDefault(size = 500) Pageable pageable) {
        Page<EventoDTO> lista = eventoService.getEventiByNomeBand(nomeBand, pageable);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
    //Filtra eventi per nome Location, anche parziale, esclude eventi passati e ordina per data evento ASC
    //es. /eventi/location?location=teatro
    @GetMapping("/location")
    public ResponseEntity<Page<EventoDTO>> getEventiByLocation(
            @RequestParam String location, @PageableDefault(size = 500) Pageable pageable) {
        Page<EventoDTO> lista = eventoService.getEventiByLocation(location, pageable);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    //Filtra eventi per nome evento, anche parziale, escludendo eventi passati.
    @GetMapping("/nome")
    public ResponseEntity<Page<EventoDTO>> getEventiByNome(@RequestParam String nome,@PageableDefault(size = 100) Pageable pageable)  {
        Page<EventoDTO> lista = eventoService.getEventiByNome(nome, pageable);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // Endpoint per ottenere 10 most popular eventi(con più partecipazioni)
    @GetMapping("/top")
    public ResponseEntity<Page<EventoDTO>> getTopEventiByPartecipazioni(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("partecipazioni")));
        Page<EventoDTO> topEventi = eventoService.getTopEventiByPartecipazioni(pageable);
        return new ResponseEntity<>(topEventi, HttpStatus.OK);
    }

}
