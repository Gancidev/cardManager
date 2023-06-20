package com.gancidev.cardmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gancidev.cardmanager.dto.service.UserDto;
import com.gancidev.cardmanager.model.User;
import com.gancidev.cardmanager.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /* API per il recupero dei dati di un utente*/
    @GetMapping("{id}")
    public User getById(@PathVariable("id") Long id) {
        return this.userService.getById(id);
    }

    
    /* API per la creazione di un utente*/
    @PostMapping("/create")
    public User create(@RequestBody UserDto dto) {
        return this.userService.create(dto);
    }

    
    /* API per bloccare e sbloccare un utente*/
    @PostMapping("/blockUnblock")
    public User blockUnblock(@RequestBody UserDto dto) {
        return this.userService.blockUnblockUser(dto);
    }
}