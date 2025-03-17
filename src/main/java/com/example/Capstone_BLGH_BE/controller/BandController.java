package com.example.Capstone_BLGH_BE.controller;

import com.example.Capstone_BLGH_BE.model.payload.BandDTO;
import com.example.Capstone_BLGH_BE.service.BandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bands")
public class BandController {
    @Autowired
    BandService bandService;

    //Ritorna tutte le band
    @GetMapping("")
    public ResponseEntity<Page<BandDTO>> getAllEventi(@PageableDefault(size = 500) Pageable page) {
        Page<BandDTO> bands = bandService.getAllBands(page);
        return new ResponseEntity<>(bands, HttpStatus.OK);
    }

    // Cerca bands per nome, anche parziale. Ritorna lista di band con nome simile.
    // nome?nome=rock
    @GetMapping("/nome")
    public ResponseEntity<List<BandDTO>> searchBandByNome(@RequestParam String nome) {
        List<BandDTO> bands = bandService.findBandsByNome(nome);
        return new ResponseEntity<>(bands, HttpStatus.OK);
    }

    // Cerca bands per genere, anche parziale. Ritorna lista di band
    // genere?genere=rock
    @GetMapping("/genere")
    public ResponseEntity<List<BandDTO>> searchBandByGenere(@RequestParam String genere) {
        List<BandDTO> bands = bandService.findBandsByGenere(genere);
        return new ResponseEntity<>(bands, HttpStatus.OK);
    }

    //Ritorna dettagli di una band
    @GetMapping("/{idBand}")
    public ResponseEntity<BandDTO> findBandById(@PathVariable Long idBand) {
        BandDTO band = bandService.findBandById(idBand);
        return new ResponseEntity<>(band, HttpStatus.OK);
    }
}
