package com.example.Capstone_BLGH_BE.model.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class BandDTO {
    private long id;

    @NotBlank(message = "Il nome della band è obbligatorio")
    private String nomeBand;

    @NotBlank(message = "La biografia della band è obbligatoria")
    private String bio;

    @NotBlank(message = "Il genere musicale è obbligatorio")
    private String genereMusicale;

    @NotBlank(message = "L'URL della foto della band è obbligatorio")
    @URL(message = "Inserisci un URL valido per la foto della band")
    private String fotoBand;

    @URL(message = "Inserisci un URL valido per il sito web")
    private String sitoWeb;

    @URL(message = "Inserisci un URL valido per Instagram")
    private String instagram;

    @URL(message = "Inserisci un URL valido per Facebook")
    private String facebook;

    @URL(message = "Inserisci un URL valido per Spotify")
    private String spotify;

    @URL(message = "Inserisci un URL valido per SoundCloud")
    private String soundcloud;

    @URL(message = "Inserisci un URL valido per YouTube")
    private String youtube;
}