package com.example.Capstone_BLGH_BE.service;

import com.example.Capstone_BLGH_BE.model.entities.Band;
import com.example.Capstone_BLGH_BE.model.entities.Evento;
import com.example.Capstone_BLGH_BE.model.exceptions.EventDuplicateException;
import com.example.Capstone_BLGH_BE.model.exceptions.NotFoundException;
import com.example.Capstone_BLGH_BE.model.payload.EventoDTO;
import com.example.Capstone_BLGH_BE.model.payload.request.EventoDTORequest;
import com.example.Capstone_BLGH_BE.repository.BandDAORepository;
import com.example.Capstone_BLGH_BE.repository.EventoDAORepository;
import com.example.Capstone_BLGH_BE.repository.PartecipazioneDAORepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    @Autowired
    PartecipazioneDAORepository partecipazioneRepo;

    //-----------------------------METODI PUBLIC---------------------------------

    //GetAll - Ritorna tutti gli eventi - paginati, esclude quelli passati e li ordina dal piu recente
    public Page<EventoDTO> getAllEventi(Pageable page) {
        LocalDate oggi = LocalDate.now();
        Page<Evento> listaEventi = eventoRepo.findByDataGreaterThanEqualOrderByDataAsc(oggi, page);
        return convertToDtoList(listaEventi);
    }

    // Trova evento da ID e ritorna i dettagli
    public EventoDTO findEventoById(long idEvento) {
        Optional<Evento> eventoRicercato = eventoRepo.findById(idEvento);
        if (eventoRicercato.isPresent()) {
            EventoDTO e = entity_dto(eventoRicercato.get());
            return e;
        } else {
            throw new NotFoundException("Nessun evento trovato con id: " + idEvento);
        }
    }

    //Get all eventi filtrati per data specifica
    public Page<EventoDTO> getEventiByData(LocalDate data, Pageable page) {
        Page<Evento> eventi = eventoRepo.findByDataOrderByDataAsc(data, page);
        return convertToDtoList(eventi);
    }

    // Get all eventi filtrati per nome band
    public Page<EventoDTO> getEventiByNomeBand(String nomeBand, Pageable page) {
        LocalDate oggi = LocalDate.now();
        Page<Evento> eventi = eventoRepo.findByBandNomeBandContainingIgnoreCaseAndDataGreaterThanEqualOrderByDataAsc(nomeBand, oggi, page);
        return convertToDtoList(eventi);
    }

    // Get all eventi filtrati per location
    public Page<EventoDTO> getEventiByLocation(String location, Pageable page) {
        LocalDate oggi = LocalDate.now();
        Page<Evento> eventi = eventoRepo.findByLocationContainingIgnoreCaseAndDataGreaterThanEqualOrderByDataDesc(location, oggi, page);
        return convertToDtoList(eventi);
    }

    //EVENTI MOST POPULAR - get eventi con più partecipazioni(stato parteciperò)
    public Page<EventoDTO> getTopEventiByPartecipazioni(Pageable page) {
        Page<Object[]> results = partecipazioneRepo.findTopEventiByPartecipazioni(page);
        List<EventoDTO> listaEventiDTO = new ArrayList<>();

        for (Object[] result : results) {
            Evento evento = (Evento) result[0]; // Il primo oggetto è l'evento
            EventoDTO eventoDTO = entity_dto(evento);
            listaEventiDTO.add(eventoDTO);
        }
        return new PageImpl<>(listaEventiDTO);
    }

    //-----------------------------METODI ADMIN---------------------------------
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

    //Crea nuovo evento
    public String createEvento(EventoDTORequest eventoDTORequest) {
        checkDuplicati(eventoDTORequest.getNome());
        //Recupero la band da associare all'evento
        Band band = bandRepo.findById(eventoDTORequest.getBandId())
                .orElseThrow(() -> new NotFoundException("Nessuna Band trovata con id: " + eventoDTORequest.getBandId()));

        Evento nuovoEvento = dtoReq_entity(eventoDTORequest);
        nuovoEvento.setBand(band);
        Evento eventoSalvato = eventoRepo.save(nuovoEvento);
        return "Evento: " + nuovoEvento.getNome() + " ,salvato con Id: " + eventoSalvato.getId();
    }

    //Check eventi duplicati
    public void checkDuplicati(String nome) {
        if (eventoRepo.existsByNome(nome)) {
            throw new EventDuplicateException("Esiste già un evento con questo nome. Inserisci un nome evento diverso.");
        }
    }

    //modifica completa di evento
    public String updateEvento(EventoDTORequest dto, long idEvento) {
        Evento eventoRicercato = eventoRepo.findById(idEvento)
                .orElseThrow(() -> new NotFoundException("Evento non trovato"));
        Band band = bandRepo.findById(dto.getBandId())
                .orElseThrow(() -> new NotFoundException("Nessuna Band trovata con id: " + dto.getBandId()));

        eventoRicercato.setNome(dto.getNome());
        eventoRicercato.setDescrizione(dto.getDescrizione());
        eventoRicercato.setLocation(dto.getLocation());
        eventoRicercato.setData(dto.getData());
        eventoRicercato.setOra(dto.getOra());
        eventoRicercato.setLocandina(dto.getLocandina());
        eventoRicercato.setBand(band);
        return "Evento con id: " + eventoRicercato.getId() + ", modificato con successo!";
    }

    //---------------------Travasi DTO----------------------------

    public Evento dtoReq_entity(EventoDTORequest dto) {
        Evento e = new Evento();
        e.setNome(dto.getNome());
        e.setDescrizione(dto.getDescrizione());
        e.setLocation(dto.getLocation());
        e.setData(dto.getData());
        e.setOra(dto.getOra());
        e.setLocandina(dto.getLocandina());
        e.setPrezzoIngresso(dto.getPrezzoIngresso());
        e.setUrlEvento(dto.getUrlEvento());
        return e;
    }

    public EventoDTO entity_dto(Evento e) {
        EventoDTO dto = new EventoDTO();
        dto.setId(e.getId());
        dto.setNome(e.getNome());
        dto.setDescrizione(e.getDescrizione());
        dto.setLocation(e.getLocation());
        dto.setData(e.getData());
        dto.setOra(e.getOra());
        dto.setLocandina(e.getLocandina());
        dto.setBand(e.getBand());
        dto.setPrezzoIngresso(e.getPrezzoIngresso());
        dto.setUrlEvento(e.getUrlEvento());
        return dto;
    }
    //Avendo riutilizzato più volte il codice in questione
    //ho preferito creare una funzione dedicata per evitare troppe ripetizioni.
    public Page<EventoDTO> convertToDtoList(Page<Evento> eventi) {
        List<EventoDTO> listaEventiDTO = new ArrayList<>();
        for (Evento e : eventi.getContent()) {
            EventoDTO eventoDTO = entity_dto(e);
            listaEventiDTO.add(eventoDTO);
        }
        return new PageImpl<>(listaEventiDTO);
    }
}
