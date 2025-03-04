package com.example.Capstone_BLGH_BE.security;

import com.example.Capstone_BLGH_BE.model.entities.Utente;
import com.example.Capstone_BLGH_BE.model.exceptions.UnauthorizedException;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String JWTSECRET;
    private long scadenza = 15;

    // Oggetto che occorre per la validazione
    private JwtParser JWTPARSER;

    @PostConstruct
    public void init() {
        JWTPARSER = Jwts.parser().setSigningKey(JWTSECRET);
    }

    //Metodo di creazione Token. Recupera le info da Utente e le inserisce nel Token finale in formato String
    public String creaToken(Utente utente) {
        // Impostazione del Claims (Payload)
        Claims claims = Jwts.claims().setSubject(utente.getUsername());
        claims.put("roles", utente.getRuolo());
        claims.put("firstname", utente.getNome());
        claims.put("lastname", utente.getCognome());
        Date dataCreazioneToken = new Date();
        Date dataScadenza = new Date(dataCreazioneToken.getTime() + TimeUnit.MINUTES.toMillis(scadenza));

        // CREAZIONE TOKEN: claims, data expiration, firma con tipologia algoritmo e la chiave
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(dataScadenza)
                .signWith(SignatureAlgorithm.HS256, JWTSECRET)
                .compact();

        return token;
    }

    //Estrazione del TOKEN in arrivo all'interno della request
    public String recuperoToken(HttpServletRequest request) throws UnauthorizedException {

        // Recupero dall'header della richiesta il token con prefisso
        String bearerToken = request.getHeader("Authorization");

        // Il token è presente? Inizia con "Bearer " ?
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // Ritorna il token senza prefisso
            return bearerToken.substring(7);
        } else throw new UnauthorizedException("Fornire un token valido.");
    }

    //Metodo di validazione del Token. Recupera il Token e ne estrae solo il payload.
    public Claims validaClaims(HttpServletRequest request) throws UnauthorizedException {
        try {
            String token = recuperoToken(request);
            return JWTPARSER.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException ex) {
            request.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            request.setAttribute("token invalido", ex.getMessage());
            throw ex;
        }
    }


    //Metodo che controlla la scadenza del Token
    public boolean checkExpiration(Claims claims) {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception ex) {
            throw ex;
        }

    }
}
