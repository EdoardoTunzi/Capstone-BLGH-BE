package com.example.Capstone_BLGH_BE.service;

import com.example.Capstone_BLGH_BE.model.entities.Band;
import com.example.Capstone_BLGH_BE.model.exceptions.NotFoundException;
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


    //-----------------------------METODI PUBLIC---------------------------------
    //Find band by id
    public BandDTO findBandById(long idBand) {
        Band bandTrovata = bandRepo.findById(idBand)
                .orElseThrow(() -> new NotFoundException("Nessuna band trovata con id: " + idBand));
        BandDTO b = entity_dto(bandTrovata);
        return b;
    }

    //Find band by Nome(anche parziale, ritorna lista di bandDTO)
    public List<BandDTO> findBandsByNome(String nome) {
        List<Band> listaBandtrovate = bandRepo.findByNomeBandContainingIgnoreCase(nome);
        List<BandDTO> listaBandDto = new ArrayList<>();

        for (Band b : listaBandtrovate) {
            BandDTO dto = entity_dto(b);
            listaBandDto.add(dto);
        }
        return listaBandDto;
    }

    //Find band by Genere(anche parziale, ritorna lista di bandDTO)
    public List<BandDTO> findBandsByGenere(String genere) {
        List<Band> listaBandtrovate = bandRepo.findByGenereMusicaleContainingIgnoreCase(genere);
        List<BandDTO> listaBandDto = new ArrayList<>();

        for (Band b : listaBandtrovate) {
            BandDTO dto = entity_dto(b);
            listaBandDto.add(dto);
        }
        return listaBandDto;
    }



    //Get all band
    public Page<BandDTO> getAllBands(Pageable page) {
        Page<Band> listaBands = bandRepo.findAll(page);
        List<BandDTO> listaBandDto = new ArrayList<>();
        for (Band b : listaBands.getContent()) {
            BandDTO bandDTO = entity_dto(b);
            listaBandDto.add(bandDTO);
        }
        return new PageImpl<>(listaBandDto, page, listaBands.getTotalElements());
    }

    //-----------------------------METODI ADMIN---------------------------------
    //Crea nuova band
    public String createBand(BandDTO bandDTO) {
        Band nuovaBand = dto_entity(bandDTO);
        Band bandSalvata = bandRepo.save(nuovaBand);
        return "Band: " + bandSalvata.getNomeBand() + " , salvata con id: " + bandSalvata.getId();
    }

    //Modifica info band
    public String updateBand(BandDTO dto, long idBand) {
        Band bandTrovata = bandRepo.findById(idBand)
                .orElseThrow(() -> new NotFoundException("Nessuna Band trovata con id: " + idBand));

        bandTrovata.setNomeBand(dto.getNomeBand());
        bandTrovata.setBio(dto.getBio());
        bandTrovata.setGenereMusicale(dto.getGenereMusicale());
        bandTrovata.setFotoBand(dto.getFotoBand());
        bandTrovata.setSitoWeb(dto.getSitoWeb());
        bandTrovata.setInstagram(dto.getInstagram());
        bandTrovata.setFacebook(dto.getFacebook());
        bandTrovata.setSpotify(dto.getSpotify());
        bandTrovata.setSoundcloud(dto.getSoundcloud());
        bandTrovata.setYoutube(dto.getYoutube());
        return "Band con id: " + bandTrovata.getId() + " , modificata con successo.";
    }

    //Modifica fotoBand
    public String updateFotoBandById(long idBand, String urlImg) {
        Band bandTrovata = bandRepo.findById(idBand)
                .orElseThrow(() -> new NotFoundException("Band non trovata con questo id"));
        bandTrovata.setFotoBand(urlImg);
        return "Immagine della band modificata con successo!";
    }

    //Cancella band
    public String deleteBandById(long idBand) {
        Band b = bandRepo.findById(idBand)
                .orElseThrow(() -> new NotFoundException("Nessuna band trovata con questo id"));
        bandRepo.delete(b);
        return "Band con id: " + idBand + " eliminata con successo!";
    }


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
        dto.setId(b.getId());
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
