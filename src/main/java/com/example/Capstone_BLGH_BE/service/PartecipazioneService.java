package com.example.Capstone_BLGH_BE.service;

import com.example.Capstone_BLGH_BE.model.entities.Evento;
import com.example.Capstone_BLGH_BE.model.entities.Partecipazione;
import com.example.Capstone_BLGH_BE.model.entities.Utente;
import com.example.Capstone_BLGH_BE.model.enumerations.StatoPartecipazione;
import com.example.Capstone_BLGH_BE.model.exceptions.BadRequestException;
import com.example.Capstone_BLGH_BE.model.exceptions.NotFoundException;
import com.example.Capstone_BLGH_BE.model.exceptions.UnauthorizedException;
import com.example.Capstone_BLGH_BE.model.payload.response.PartecipazioneDTOResponse;
import com.example.Capstone_BLGH_BE.model.payload.request.PartecipazioneDTORequest;
import com.example.Capstone_BLGH_BE.repository.EventoDAORepository;
import com.example.Capstone_BLGH_BE.repository.PartecipazioneDAORepository;
import com.example.Capstone_BLGH_BE.repository.UtenteDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PartecipazioneService {
    @Autowired
    PartecipazioneDAORepository partecipazioneRepo;
    @Autowired
    EventoDAORepository eventoRepo;

    @Autowired
    UtenteDAORepository utenteRepo;
    //-----------------------------METODI UTENTE LOGGATO---------------------------------

    //Get partecipazioni di un utente loggato per statoPartecipazione
    // (parteciperò, partecipato, mi interessa)
    public Page<PartecipazioneDTOResponse> getPartecipazioniUtenteByStato(String username, StatoPartecipazione stato, Pageable page) {
        Utente utente = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Utente non trovato"));
        Page<Partecipazione> listaPartecipazioni = partecipazioneRepo.findByUtenteAndStatoPartecipazioneOrderByEventoData(utente, stato, page);
        List<PartecipazioneDTOResponse> listaDTO = new ArrayList<>();
        for (Partecipazione p : listaPartecipazioni) {
            PartecipazioneDTOResponse partDTO = entity_Dto(p);
            listaDTO.add(partDTO);
        }
        return new PageImpl<>(listaDTO);
    }

    //Crea partecipazione
    public String createPartecipazione(String username, PartecipazioneDTORequest dtoRequest) {
        Utente utente = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Utente non trovato"));
        Evento evento = eventoRepo.findById(dtoRequest.getEventoId())
                .orElseThrow(() -> new NotFoundException("Evento non trovato"));
        // Prima verifica se esiste già una partecipazione per questo utente ed evento
        if (partecipazioneRepo.existsByUtenteAndEvento(utente, evento)) {
            throw new BadRequestException("Hai già una partecipazione per questo evento!");
        }
        Partecipazione nuovaPartecipazione = new Partecipazione();
        nuovaPartecipazione.setUtente(utente);
        nuovaPartecipazione.setEvento(evento);
        nuovaPartecipazione.setStatoPartecipazione(dtoRequest.getStatoPartecipazione());
        Partecipazione partecipazioneSalvata = partecipazioneRepo.save(nuovaPartecipazione);
        return "Partecipazione salvata con successo, con id: " + partecipazioneSalvata.getId();
    }

    //Modifica stato partecipazione
    public String updateStatoPartecipazione(long idPartecipazione, StatoPartecipazione nuovoStato, String username) {
        Partecipazione partecipazioneTrovata = partecipazioneRepo.findById(idPartecipazione)
                .orElseThrow(() -> new NotFoundException("Partecipazione non trovata"));

        //qui controllo se l'utente loggato è proprietario della partecipazione
        if (!partecipazioneTrovata.getUtente().getUsername().equals(username)) {
            throw new UnauthorizedException("Non puoi modificare lo stato di questa partecipazione");
        }

        partecipazioneTrovata.setStatoPartecipazione(nuovoStato);
        return "Stato della partecipazione aggiornato a: " + nuovoStato;
    }

    //Delete partecipazione a evento da idEvento
    public String deletePartecipazione(long idPartecipazione) {
        Partecipazione partecipazione = partecipazioneRepo.findById(idPartecipazione)
                .orElseThrow(()-> new NotFoundException("Partecipazione non trovata"));
        partecipazioneRepo.delete(partecipazione);
        return "Partecipazione eliminata con successo!";
    }

    // -----------------------------TRAVASI DTO----------------------------------
    public PartecipazioneDTOResponse entity_Dto(Partecipazione p) {
        PartecipazioneDTOResponse dto = new PartecipazioneDTOResponse();
        dto.setId(p.getId());
        dto.setEvento(p.getEvento());
        dto.setStatoPartecipazione(p.getStatoPartecipazione());
        return dto;
    }
}
