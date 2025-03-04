package com.example.Capstone_BLGH_BE.model.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrazioneRequest {
    private String nome;
    private String cognome;

    @NotBlank(message = "Username è un campo obbligatorio")
    private String username;

    @NotBlank(message = "email è un campo obbligatorio")
    @Email(message = "Il formato email inserito non è valido")
    private String email;

    @NotBlank(message = "Password è un campo obbligatorio")
    private String password;

    private String avatar;
    private String ruolo;
}
