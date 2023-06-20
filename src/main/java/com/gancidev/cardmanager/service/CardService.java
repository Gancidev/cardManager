package com.gancidev.cardmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gancidev.cardmanager.dto.service.CardDto;
import com.gancidev.cardmanager.model.Card;
import com.gancidev.cardmanager.repository.CardRepository;

@Service
public class CardService {
    
    @Autowired
    private CardRepository cardRepository;

    /* Servizio per il recupero dei dati di una carta*/
    public Card getByNumber(String number) {
        return this.cardRepository.findByNumber(number).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* Servizio per la creazione di una carta*/
    public Card create(CardDto dto) {
        Card card = new Card();
        card.setNumber(dto.getNumber());
        card.setCredit(dto.getCredit());
        card.setUser_id(dto.getUser_id());
        card.setBlocked(Boolean.FALSE);
        return this.cardRepository.save(card);
    }

    /* Servizio per accreditare o effettuare un pagamento con una carta*/
    public Card updateCredit(CardDto dto) {
        Double creditCard = this.cardRepository.getCardCredit(dto.getNumber()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.cardRepository.updateCardCredit(creditCard+dto.getCredit(), dto.getNumber());
        return this.cardRepository.findByNumber(dto.getNumber()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* Servizio per bloccare e sbloccare una carta*/
    public Card lockUnlockCard(CardDto dto) {
        this.cardRepository.lockUnlockCard(dto.getNumber());
        return this.cardRepository.findByNumber(dto.getNumber()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}