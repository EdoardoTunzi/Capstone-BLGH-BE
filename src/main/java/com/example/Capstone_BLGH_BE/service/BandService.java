package com.example.Capstone_BLGH_BE.service;

import com.example.Capstone_BLGH_BE.model.entities.Band;
import com.example.Capstone_BLGH_BE.model.payload.BandDTO;
import com.example.Capstone_BLGH_BE.repository.BandDAORepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BandService {
    @Autowired
    BandDAORepository bandRepo;

    //Crea nuova band
    public String createBand(BandDTO bandDTO) {
        Band nuovaBand = dto_entity(bandDTO);
        Band bandSalvata = bandRepo.save(nuovaBand);
        return "Band: " + bandSalvata.getNomeBand()+ " , salvata con id: " + bandSalvata.getId();
    }
    //get band by id
    //get all band
    //modifica info band
    //modifica fotoBand
    //cancella band


    //-----------------------------TRAVASI DTO----------------------------------
    public Band dto_entity(BandDTO dto) {
        Band b = new Band();
        b.setNomeBand(dto.getNomeBand());
        b.setBio(dto.getBio());
        b.setGenereMusicale(dto.getGenereMusicale());
        b.setFotoBand(dto.getFotoBand());
        b.setSitoWeb(dto.getSitoWeb());
        b.setInstagram(dto.getInstagram());
        b.setFacebook(dto.getFacebook());
        b.setSpotify(dto.getSpotify());
        b.setSoundcloud(dto.getSoundcloud());
        b.setYoutube(dto.getYoutube());
        return b;
    }
}
