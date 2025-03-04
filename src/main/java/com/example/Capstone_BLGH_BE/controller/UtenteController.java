package com.example.Capstone_BLGH_BE.controller;

import com.example.Capstone_BLGH_BE.model.entities.Utente;
import com.example.Capstone_BLGH_BE.model.exceptions.EmailDuplicateException;
import com.example.Capstone_BLGH_BE.model.exceptions.UsernameDuplicateException;
import com.example.Capstone_BLGH_BE.model.payload.request.RegistrazioneRequest;
import com.example.Capstone_BLGH_BE.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UtenteController {

    @Autowired
    UtenteService utenteService;



}
