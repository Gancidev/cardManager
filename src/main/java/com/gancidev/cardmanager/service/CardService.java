package com.gancidev.cardmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gancidev.cardmanager.dto.controller.CardRequest;
import com.gancidev.cardmanager.dto.service.CardToFE;
import com.gancidev.cardmanager.model.Card;
import com.gancidev.cardmanager.model.User;
import com.gancidev.cardmanager.repository.CardRepository;
import com.gancidev.cardmanager.repository.TransactionRepository;
import com.gancidev.cardmanager.repository.UserRepository;

@Service
public class CardService {
    
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    /* Servizio per il recupero dei dati di una carta*/
    public Card getByNumber(String number) {
        return this.cardRepository.findByNumber(number).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* Servizio per il recupero del numero di carte di un cliente*/
    public Integer getNumberOfMyCard(Long user_id) {
        return this.cardRepository.numberOfMyCard(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* Servizio per il recupero delle carte di un cliente*/
    public List<CardToFE> getListOfCards(Long user_id) {
        List<Card> listCards =  this.cardRepository.getCardByUserId(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<CardToFE> listCardsCompleted = new ArrayList<>();
        listCards.forEach(card->{
            CardToFE tmp = new CardToFE(card);
            User tmpCustomer = this.userRepository.findById(card.getUser_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            tmp.setEmail(tmpCustomer.getEmail());
            listCardsCompleted.add(tmp);
        });
        return listCardsCompleted;
    }

    /* Servizio per il recupero delle carte di un cliente*/
    public List<CardToFE> getAllCards() {
        List<Card> listCards =  this.cardRepository.getAllCard().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<CardToFE> listCardsCompleted = new ArrayList<>();
        listCards.forEach(card->{
            CardToFE tmp = new CardToFE(card);
            User tmpCustomer = this.userRepository.findById(card.getUser_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            tmp.setEmail(tmpCustomer.getEmail());
            listCardsCompleted.add(tmp);
        });
        return listCardsCompleted;
    }

    /* Servizio per il recupero del credito di una carta*/
    public Double getCardCredit(String number) {
        return this.cardRepository.getCardCredit(number).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* Servizio per la creazione di una carta*/
    public Card create(CardRequest dto) {
        Card card = new Card();
        card.setNumber(dto.getNumber());
        card.setCredit(dto.getCredit());
        card.setUser_id(dto.getUser_id());
        card.setExpiration(dto.getExpiration());
        card.setCvv(dto.getCvv());
        card.setBlocked(Boolean.FALSE);
        try {
            return this.cardRepository.save(card);
        } catch (Exception e) {
            if(e.getMessage().contains("Duplicate entry")){
                return new Card();
            }
            return null;
        }
    }

    /* Servizio per la cancellazione della sessione*/
    public Boolean deleteCard(CardRequest dto) {
        try {
            this.transactionRepository.deleteTransaction(dto.getNumber());
            this.cardRepository.deleteCard(dto.getNumber());
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.TRUE;
        }
    }

    /* Servizio per accreditare o effettuare un pagamento con una carta*/
    public Card updateCredit(CardRequest dto) {
        Double creditCard = this.cardRepository.getCardCredit(dto.getNumber()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.cardRepository.updateCardCredit((Math.round((creditCard + dto.getCredit()) * 100.0) / 100.0), dto.getNumber());
        return this.cardRepository.findByNumber(dto.getNumber()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* Servizio per bloccare e sbloccare una carta*/
    public Card lockUnlockCard(CardRequest dto) {
        this.cardRepository.lockUnlockCard(dto.getNumber());
        return this.cardRepository.findByNumber(dto.getNumber()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}