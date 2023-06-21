package com.gancidev.cardmanager.service;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gancidev.cardmanager.dto.controller.UserRequest;
import com.gancidev.cardmanager.model.Session;
import com.gancidev.cardmanager.model.User;
import com.gancidev.cardmanager.repository.SessionRepository;
import com.gancidev.cardmanager.repository.UserRepository;

@Service
public class SessionService {
    
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    /* Servizio per il recupero dei dati di una carta*/
    public Session getByToken(String token) {
        return this.sessionRepository.findByToken(token).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* Servizio per la verifica delle credenziali*/
    public Optional<User> checkCredential(UserRequest dto) {
        return this.userRepository.checkCredential(dto.getEmail(), dto.getPassword());
    }

    /* Servizio per la creazione della sessione*/
    public Session createSession(Optional<User> optionalUser) {
        User user = optionalUser.get();
        Session sessione = new Session();
        sessione.setUser(user);
        sessione.setPrivileges(user.getRole());
        sessione.setToken(generateToken());
        try {
            return this.sessionRepository.save(sessione);
        } catch (Exception e) {
            return null;
        }
    }

    /* Servizio per la cancellazione della sessione*/
    public Boolean deleteSession(Session sessione) {
        try {
            this.sessionRepository.deleteSession(sessione.getToken());
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.TRUE;
        }
    }

    private String generateToken(){
        String token;
        Boolean condition;
        do{
            token = RandomStringUtils.randomAlphanumeric(32);
            condition = checkToken(token);
        }while(condition);
        return token;
    }

    /* Servizio per la verifica del token generato*/
    public Boolean checkToken(String token) {
        Optional<Session> sessione = this.sessionRepository.findByToken(token);
        if(sessione!=null){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}