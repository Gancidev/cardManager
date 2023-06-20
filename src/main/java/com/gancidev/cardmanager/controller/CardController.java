package com.gancidev.cardmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gancidev.cardmanager.dto.service.CardDto;
import com.gancidev.cardmanager.model.Card;
import com.gancidev.cardmanager.service.CardService;

@RestController
@RequestMapping("card")
public class CardController {

    @Autowired
    private CardService cardService;

    /* API per il recupero dei dati di una carta*/
    @GetMapping("{number}")
    public Card getById(@PathVariable("number") String id) {
        return this.cardService.getByNumber(id);
    }

    /* API per la creazione di una carta*/
    @PostMapping("/create")
    public Card create(@RequestBody CardDto dto) {
        return this.cardService.create(dto);
    }

    /* API per accreditare o effettuare un pagamento con una carta*/
    @PostMapping("/credit")
    public Card updateCredit(@RequestBody CardDto dto) {
        return this.cardService.updateCredit(dto);
    }

    /* API per bloccare e sbloccare una carta*/
    @PostMapping("/lockUnlock")
    public Card lockUnlock(@RequestBody CardDto dto) {
        return this.cardService.lockUnlockCard(dto);
    }
}