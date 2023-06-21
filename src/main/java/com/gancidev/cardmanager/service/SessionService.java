package com.gancidev.cardmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gancidev.cardmanager.model.Session;
import com.gancidev.cardmanager.repository.SessionRepository;

@Service
public class SessionService {
    
    @Autowired
    private SessionRepository sessionRepository;

    /* Servizio per il recupero dei dati di una carta*/
    public Session getByToken(String token) {
        return this.sessionRepository.findByToken(token).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}