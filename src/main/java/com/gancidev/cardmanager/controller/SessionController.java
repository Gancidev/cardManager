package com.gancidev.cardmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gancidev.cardmanager.model.Session;
import com.gancidev.cardmanager.service.SessionService;

@RestController
@RequestMapping("session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    /* API per il recupero dei dati di un utente*/
    @GetMapping("{token}")
    public Session getByToken(@PathVariable("token") String token) {
        return this.sessionService.getByToken(token);
    }
}