package com.example.Capstone_BLGH_BE.service;

import com.example.Capstone_BLGH_BE.model.entities.Band;
import com.example.Capstone_BLGH_BE.model.entities.Evento;
import com.example.Capstone_BLGH_BE.model.exceptions.EventDuplicateException;
import com.example.Capstone_BLGH_BE.model.exceptions.NotFoundException;
import com.example.Capstone_BLGH_BE.model.payload.EventoDTO;
import com.example.Capstone_BLGH_BE.model.payload.request.EventoDTORequest;
import com.example.Capstone_BLGH_BE.repository.BandDAORepository;
import com.example.Capstone_BLGH_BE.repository.EventoDAORepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventoService {
    @Autowired
    EventoDAORepository eventoRepo;
    @Autowired
    BandDAORepository bandRepo;

    //Crea nuovo evento
    public String createEvento(EventoDTORequest eventoDTORequest) {
        checkDuplicati(eventoDTORequest.getNome());
        //Recupero la band da associare all'evento
        Band band = bandRepo.findById(eventoDTORequest.getBandId())
                .orElseThrow(()-> new NotFoundException("Nessuna Band trovata con id: " + eventoDTORequest.getBandId()));

        Evento nuovoEvento = dtoReq_entity(eventoDTORequest);
        nuovoEvento.setBand(band);
        Evento eventoSalvato = eventoRepo.save(nuovoEvento);
        return "Evento: " + nuovoEvento.getNome() + " ,salvato con Id: " + eventoSalvato.getId();
    }

    //Check eventi duplicati
    public void checkDuplicati(String nome) throws EventDuplicateException {
        if (eventoRepo.existsByNome(nome)) {
            throw new EventDuplicateException("Esiste già un evento con questo nome. Inserisci un nome evento diverso.");
        }
    }

    //GetAll - Ritorna tutti gli eventi - paginati
    public Page<EventoDTO> getAllEventi(Pageable page) {
        Page<Evento> listaEventi = eventoRepo.findAll(page);
        List<EventoDTO> listaEventiDTO = new ArrayList<>();
        for (Evento e : listaEventi.getContent()) {
            EventoDTO eventoDTO = entity_dto(e);
            listaEventiDTO.add(eventoDTO);
        }
        return new PageImpl<>(listaEventiDTO);
    }

    // Trova evento da ID
    public EventoDTO findEventoById(long idEvento) {
        Optional<Evento> eventoRicercato = eventoRepo.findById(idEvento);
        if (eventoRicercato.isPresent()) {
            EventoDTO e = entity_dto(eventoRicercato.get());
            return e;
        } else {
            throw new NotFoundException("Nessun evento trovato con id: " + idEvento);
        }
    }
    //modifica completa di evento
    public String updateEvento(EventoDTO dto, long idEvento) {
        Optional<Evento> eventoRicercato = eventoRepo.findById(idEvento);
        if (eventoRicercato.isPresent()) {
            Evento e = eventoRicercato.get();
            e.setNome(dto.getNome());
            e.setDescrizione(dto.getDescrizione());
            e.setLocation(dto.getLocation());
            e.setData(dto.getData());
            e.setOra(dto.getOra());
            e.setLocandina(dto.getLocandina());
            e.setBand(dto.getBand());
            eventoRepo.save(e);
            return "Evento con id: "+e.getId()+ ", modificato con successo!";
        } else {
            throw new NotFoundException("Errore nella modifica. Nessun evento trovato con id: " + idEvento);
        }
    }

    //Elimina evento
    public String deleteEvento(long idEvento) {
        Optional<Evento> eventoRicercato = eventoRepo.findById(idEvento);
        if (eventoRicercato.isPresent()) {
            eventoRepo.delete(eventoRicercato.get());
            return "Evento con id: " + idEvento + " eliminato con successo!";
        } else {
            throw new NotFoundException("Errore nel delete! Nessun evento trovato con id: " + idEvento);
        }
    }

    //Get all eventi di una band

    //---------------------Travasi DTO----------------------------

    public Evento dto_entity(EventoDTO dto) {
        Evento e = new Evento();
        e.setNome(dto.getNome());
        e.setDescrizione(dto.getDescrizione());
        e.setLocation(dto.getLocation());
        e.setData(dto.getData());
        e.setOra(dto.getOra());
        e.setLocandina(dto.getLocandina());
        e.setBand(dto.getBand());
        return e;
    }

    public Evento dtoReq_entity(EventoDTORequest dto) {
        Evento e = new Evento();
        e.setNome(dto.getNome());
        e.setDescrizione(dto.getDescrizione());
        e.setLocation(dto.getLocation());
        e.setData(dto.getData());
        e.setOra(dto.getOra());
        e.setLocandina(dto.getLocandina());
        return e;
    }

    public EventoDTO entity_dto(Evento e) {
        EventoDTO dto = new EventoDTO();
        dto.setNome(e.getNome());
        dto.setDescrizione(e.getDescrizione());
        dto.setLocation(e.getLocation());
        dto.setData(e.getData());
        dto.setOra(e.getOra());
        dto.setLocandina(e.getLocandina());
        dto.setBand(e.getBand());
        return dto;
    }
}
