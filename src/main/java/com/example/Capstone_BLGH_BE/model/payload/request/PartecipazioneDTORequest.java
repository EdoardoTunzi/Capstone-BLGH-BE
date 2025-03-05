package com.example.Capstone_BLGH_BE.model.payload.request;

import com.example.Capstone_BLGH_BE.model.enumerations.StatoPartecipazione;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PartecipazioneDTORequest {

    private long id;
    @NotNull(message = "L'ID dell'evento è obbligatorio")
    private long eventoId;
    @NotNull(message = "Lo stato della partecipazione è obbligatorio")
    private StatoPartecipazione statoPartecipazione;
}
