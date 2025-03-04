package com.example.Capstone_BLGH_BE.controller;

import com.example.Capstone_BLGH_BE.model.payload.BandDTO;
import com.example.Capstone_BLGH_BE.service.BandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bands")
public class BandController {
    @Autowired
    BandService bandService;


    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<Page<BandDTO>> getAllEventi(Pageable page) {
        Page<BandDTO> bands = bandService.getAllBands(page);
        return new ResponseEntity<>(bands, HttpStatus.OK);
    }
}
