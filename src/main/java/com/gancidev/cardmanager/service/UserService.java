package com.gancidev.cardmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gancidev.cardmanager.dto.controller.UserRequest;
import com.gancidev.cardmanager.model.User;
import com.gancidev.cardmanager.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    /* Servizio per la creazione di un utente*/
    public User create(UserRequest dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setDisabled(Boolean.FALSE);
        try {
            return this.userRepository.save(user);
        } catch (Exception e) {
            if(e.getMessage().contains("Duplicate entry")){
                return new User();
            }
            return null;
        }
    }
    
    /* Servizio per bloccare e sbloccare un utente*/
    public User blockUnblockUser(UserRequest dto) {
        this.userRepository.blockUnblockUser(dto.getEmail());
        return this.userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}