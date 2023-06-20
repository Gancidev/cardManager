package com.gancidev.cardmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gancidev.cardmanager.dto.service.UserDto;
import com.gancidev.cardmanager.model.User;
import com.gancidev.cardmanager.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    /* Servizio per il recupero dei dati di un utente*/
    public User getById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    /* Servizio per la creazione di un utente*/
    public User create(UserDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setDisabled(Boolean.FALSE);
        return this.userRepository.save(user);
    }
    
    /* Servizio per bloccare e sbloccare un utente*/
    public User blockUnblockUser(UserDto dto) {
        this.userRepository.blockUnblockUser(dto.getEmail());
        return this.userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}