package com.gancidev.cardmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gancidev.cardmanager.dto.controller.CardRequest;
import com.gancidev.cardmanager.dto.controller.ResponseToFE;
import com.gancidev.cardmanager.model.Card;
import com.gancidev.cardmanager.model.Session;
import com.gancidev.cardmanager.service.CardService;

@RestController
@RequestMapping("card")
public class CardController extends AbstractController{

    @Autowired
    private CardService cardService;

    /* API per il recupero dei dati di una carta*/
    @GetMapping("{number}")
    public ResponseEntity<ResponseToFE> getByNumber(@PathVariable("number") String id) {
        Card card = this.cardService.getByNumber(id);
        card.setBlocked(null);
        card.setCard_id(null);
        card.setTransactions(null);
        card.setUser_id(null);
        return ResponseEntity.ok(new ResponseToFE(card));
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
}