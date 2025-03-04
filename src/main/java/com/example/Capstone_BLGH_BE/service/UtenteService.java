package com.example.Capstone_BLGH_BE.service;

import com.example.Capstone_BLGH_BE.model.entities.Utente;
import com.example.Capstone_BLGH_BE.model.exceptions.*;
import com.example.Capstone_BLGH_BE.model.payload.UtenteDTO;
import com.example.Capstone_BLGH_BE.model.payload.request.RegistrazioneRequest;
import com.example.Capstone_BLGH_BE.model.payload.response.LoginResponse;
import com.example.Capstone_BLGH_BE.repository.UtenteDAORepository;
import com.example.Capstone_BLGH_BE.security.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UtenteService {
    @Autowired
    UtenteDAORepository utenteRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;


    //Registrazione nuovo utente
    public String newUtente(RegistrazioneRequest dto) throws BadRequestException {
        String passwordCodificata = passwordEncoder.encode(dto.getPassword());
        checkDuplicateKey(dto.getUsername(), dto.getEmail());
        Utente nuovoUtente = registrazioneRequest_Utente(dto);
        nuovoUtente.setPassword(passwordCodificata);
        //assegnazione img avatar default
        if (dto.getAvatar() == null) {
            nuovoUtente.setAvatar("https://ibb.co/xS30My3L");
        }
        //assegnazione ruolo default - in versioni successive includi organizzatore.
        if (dto.getRuolo() == null || dto.getRuolo().equals("USER")) {
            nuovoUtente.setRuolo("USER");
        } else {
            throw new BadRequestException("Errore. Il valore inserito come ruolo, non è valido.");
        }
        //salvataggio
        Long id = utenteRepo.save(nuovoUtente).getId();
        return "Nuovo utente creato con id: " + id;
    }

    //Check duplicati email e username
    public void checkDuplicateKey(String username, String email) throws UsernameDuplicateException, EmailDuplicateException {

        if (utenteRepo.existsByUsername(username)) {
            throw new UsernameDuplicateException("Username già utilizzato, non disponibile");
        }

        if (utenteRepo.existsByEmail(email)) {
            throw new EmailDuplicateException("Email già utilizzata da un altro utente");
        }
    }

    //Gestione Login utente
    public LoginResponse login(String username, String password) {
        // 1. AUTENTICAZIONE DELL'UTENTE IN FASE DI LOGIN
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        // 2. INSERIMENTO DELL'AUTENTICAZIONE UTENTE NEL CONTESTO DELLA SICUREZZA
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 3. RECUPERO RUOLI --> String
        String ruolo = null;
        for (Object role : authentication.getAuthorities()) {
            ruolo = role.toString();
            break;
        }
        // 4. GENERO L'UTENTE
        Utente user = new Utente();
        user.setUsername(username);
        user.setRuolo(ruolo);
        // 5. GENERO IL TOKEN
        String token = jwtUtils.creaToken(user);
        // 6. CREO L'OGGETTO DI RISPOSTA AL CLIENT
        return new LoginResponse(username, token);
    }

    //Ottieni i dati dell'utente loggato
    public UtenteDTO getUtenteByUsername(String username) {
        Utente utenteTrovato = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Utente non trovato."));
        UtenteDTO dto = entity_Dto(utenteTrovato);
        return dto;
    }

    //Modifica i dati dell'utente loggato(escluso password)
    public UtenteDTO updateUtenteInfoByUsername(String username, UtenteDTO utenteDTO) {
        Utente utenteTrovato = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Utente non trovato."));
        utenteTrovato.setNome(utenteDTO.getNome());
        utenteTrovato.setCognome(utenteDTO.getCognome());
        utenteTrovato.setUsername(utenteDTO.getUsername());
        utenteTrovato.setEmail(utenteDTO.getEmail());
        utenteTrovato.setAvatar(utenteDTO.getAvatar());

        UtenteDTO dto = entity_Dto(utenteTrovato);
        return dto;
    }

    //Modifica password dell'utente loggato
    public String updatePasswordUtenteByUsername(String username, String oldPassword, String newPassword) {
        Utente utenteTrovato = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Utente non trovato."));
        //confronta la password immessa (oldPassword) con quella criptata memorizzata nel database.
        if (!passwordEncoder.matches(oldPassword, utenteTrovato.getPassword())) {
            throw new UnauthorizedException("La password vecchia/attuale che hai inserito non è corretta.");
        }
        String nuovaPasswordCodificata = passwordEncoder.encode(newPassword);
        utenteTrovato.setPassword(nuovaPasswordCodificata);

        return "Password modificata correttamente!";
    }

    // -----------------------------TRAVASI DTO----------------------------------
    //Travaso RegistrazioneRequest a Utente
    public Utente registrazioneRequest_Utente(RegistrazioneRequest request) {
        Utente utente = new Utente();
        utente.setEmail(request.getEmail());
        utente.setNome(request.getNome());
        utente.setUsername(request.getUsername());
        utente.setCognome(request.getCognome());
        return utente;
    }

    public UtenteDTO entity_Dto(Utente u) {
        UtenteDTO dto = new UtenteDTO();
        dto.setId(u.getId());
        dto.setNome(u.getNome());
        dto.setCognome(u.getCognome());
        dto.setUsername(u.getUsername());
        dto.setEmail(u.getEmail());
        dto.setAvatar(u.getAvatar());
        //password esclusa, gestita in metodo dedicato
        return dto;
    }

}
