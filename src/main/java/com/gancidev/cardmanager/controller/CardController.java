package com.gancidev.cardmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gancidev.cardmanager.dto.controller.CardRequest;
import com.gancidev.cardmanager.dto.controller.ResponseToFE;
import com.gancidev.cardmanager.dto.service.CardToFE;
import com.gancidev.cardmanager.model.Card;
import com.gancidev.cardmanager.model.Session;
import com.gancidev.cardmanager.service.CardService;

@RestController
@RequestMapping("card")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class CardController extends AbstractController{

    @Autowired
    private CardService cardService;

    /* API per il recupero dei dati di una carta*/
    @GetMapping("{number}")
    public ResponseEntity<ResponseToFE> getByNumber(@PathVariable("number") String number) {
        Double credit = this.cardService.getCardCredit(number);
        Card card = new Card();
        card.setCredit(credit);
        return ResponseEntity.ok(new ResponseToFE(card));
    }

    /* API per il recupero delle carte di un cliente*/
    @GetMapping("/list")
    public ResponseEntity<ResponseToFE> getListOfCards() {
        Session sessione = getSession();
        if(sessione!=null){
            List<CardToFE> card = this.cardService.getListOfCards(sessione.getUser().getId());
            return ResponseEntity.ok(new ResponseToFE(null, card, null, null, null));
        }
        return ResponseEntity.ok(new ResponseToFE(Boolean.TRUE));
    }

    /* API per il recupero delle carte di un cliente*/
    @GetMapping("/listAll")
    public ResponseEntity<ResponseToFE> getAllCards() {
        Session sessione = getSession();
        if(sessione!=null && sessione.getPrivileges().equals("admin")){
            List<CardToFE> card = this.cardService.getAllCards();
            return ResponseEntity.ok(new ResponseToFE(null, card, null, null, null));
        }
        return ResponseEntity.ok(new ResponseToFE(Boolean.TRUE));
    }

    /* API per la creazione di una carta*/
    @PostMapping("/create")
    public ResponseEntity<ResponseToFE> create(@RequestBody CardRequest dto) {
        Session sessione = getSession();
        if(sessione!=null && sessione.getPrivileges().equals("admin")){
            Card card = this.cardService.create(dto);
            if(card.getCard_id()!=null){
                return ResponseEntity.ok(new ResponseToFE(card));
            }
            return ResponseEntity.ok(new ResponseToFE(Boolean.TRUE, "Carta già utilizzata"));
        }
        return ResponseEntity.ok(new ResponseToFE(Boolean.TRUE));
    }

    /* API per bloccare e sbloccare una carta*/
    @PostMapping("/lockUnlock")
    public ResponseEntity<ResponseToFE> lockUnlock(@RequestBody CardRequest dto) {
        Session sessione = getSession();
        if(sessione!=null && sessione.getPrivileges().equals("admin")){
            return ResponseEntity.ok(new ResponseToFE(this.cardService.lockUnlockCard(dto)));
        }
        return ResponseEntity.ok(new ResponseToFE(Boolean.TRUE, "Errore si prega di riprovare"));
    }

    /* API per eliminare una carta*/
    @PostMapping("/delete")
    public ResponseEntity<ResponseToFE> deleteCard(@RequestBody CardRequest dto) {
        Session sessione = getSession();
        if(sessione!=null && sessione.getPrivileges().equals("admin")){
            return ResponseEntity.ok(new ResponseToFE(this.cardService.deleteCard(dto)));
        }
        return ResponseEntity.ok(new ResponseToFE(Boolean.TRUE, "Errore si prega di riprovare"));
    }
}