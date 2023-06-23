package com.gancidev.cardmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gancidev.cardmanager.model.Session;
import com.gancidev.cardmanager.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class AbstractController {

    @Autowired
	private HttpServletRequest request;

    @Autowired
    protected SessionService sessionService;

    public Session getSession() {
        String sessionId = request.getHeader("SESSION-TOKEN");
        try{
            return sessionService.getByToken(sessionId);
        }catch(ResponseStatusException r){
            return null;
        }
    }
}