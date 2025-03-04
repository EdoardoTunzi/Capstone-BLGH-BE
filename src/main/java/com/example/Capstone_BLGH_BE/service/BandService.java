package com.example.Capstone_BLGH_BE.service;

import com.example.Capstone_BLGH_BE.model.entities.Band;
import com.example.Capstone_BLGH_BE.model.payload.BandDTO;
import com.example.Capstone_BLGH_BE.repository.BandDAORepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public Page<BandDTO> getAllBands(Pageable page) {
        Page<Band> listaBands = bandRepo.findAll(page);
        List<BandDTO> listaBandDto = new ArrayList<>();
        for (Band b : listaBands.getContent()) {
            BandDTO bandDTO = entity_dto(b);
            listaBandDto.add(bandDTO);
        }
        return new PageImpl<>(listaBandDto);
    }
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

    public BandDTO entity_dto(Band b) {
        BandDTO dto = new BandDTO();
        dto.setNomeBand(b.getNomeBand());
        dto.setBio(b.getBio());
        dto.setGenereMusicale(b.getGenereMusicale());
        dto.setFotoBand(b.getFotoBand());
        dto.setSitoWeb(b.getSitoWeb());
        dto.setInstagram(b.getInstagram());
        dto.setFacebook(b.getFacebook());
        dto.setSpotify(b.getSpotify());
        dto.setSoundcloud(b.getSoundcloud());
        dto.setYoutube(b.getYoutube());
        return dto;
    }
}
