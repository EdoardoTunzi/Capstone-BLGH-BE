package com.example.Capstone_BLGH_BE.model.payload;

import com.example.Capstone_BLGH_BE.model.entities.Band;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventoDTO {
    private long id;

    @NotBlank(message = "Il nome dell'evento è obbligatorio")
    private String nome;

    @NotNull(message = "La data dell'evento è obbligatoria")
    @FutureOrPresent(message = "La data deve essere presente o futura")
    private LocalDate data;

    @NotNull(message = "L'orario dell'evento è obbligatorio")
    private LocalTime ora;

    @NotBlank(message = "La location è obbligatoria")
    private String location;

    @NotBlank(message = "La descrizione è obbligatoria")
    private String descrizione;

    @URL(message = "Inserisci un URL valido per la locandina dell'evento")
    private String locandina;

    @NotBlank(message = "Il prezzo di ingresso è obbligatorio")
    private String prezzoIngresso;

    @URL(message = "Inserisci un URL valido per la pagina dell'evento")
    private String urlEvento;

    @NotNull(message = "L'evento deve avere una band/artista)")
    private Band band;
}
