package com.example.Capstone_BLGH_BE.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class UtenteDTO {
    private long id;

    private String nome;
    private String cognome;
    @NotBlank(message = "Username è un campo obbligatorio")
    private String username;
    @NotBlank(message = "Email è un campo obbligatorio")
    @Email(message = "Il formato email inserito non è valido")
    private String email;
    @JsonIgnore
    private String password;
    @URL
    private String avatar;
}
