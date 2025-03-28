package com.example.Capstone_BLGH_BE.security;

import com.example.Capstone_BLGH_BE.model.entities.Utente;
import com.example.Capstone_BLGH_BE.repository.UtenteDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;


@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    UtenteDAORepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Utente user = repo.findByUsername(username).orElseThrow();

        UserDetails dettagliUtente= User.builder().username(user.getUsername())
                .password((user.getPassword()))
                .roles(String.valueOf(user.getRuolo())).build();

        return dettagliUtente;
    }
}
