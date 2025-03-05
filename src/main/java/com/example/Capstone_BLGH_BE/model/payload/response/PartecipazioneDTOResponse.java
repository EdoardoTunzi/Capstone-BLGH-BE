package com.example.Capstone_BLGH_BE.model.payload.response;

import com.example.Capstone_BLGH_BE.model.entities.Evento;
import com.example.Capstone_BLGH_BE.model.enumerations.StatoPartecipazione;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PartecipazioneDTOResponse {
    private long id;
    //non ritorno l'utente perchè non serve.
    //Solo l'utente loggato può accedere alle partecipazioni
    //inoltre i dettagli dell'utente non mi servono nel frontend
    @NotNull(message = "L'ID dell'evento è obbligatorio")
    private Evento evento;
    @NotNull(message = "Lo stato della partecipazione è obbligatorio")
    private StatoPartecipazione statoPartecipazione;
}
