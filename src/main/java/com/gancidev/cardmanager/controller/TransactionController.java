package com.gancidev.cardmanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gancidev.cardmanager.dto.controller.CardRequest;
import com.gancidev.cardmanager.dto.controller.ResponseToFE;
import com.gancidev.cardmanager.dto.controller.TransactionRequest;
import com.gancidev.cardmanager.dto.controller.UserRequest;
import com.gancidev.cardmanager.model.Card;
import com.gancidev.cardmanager.model.Session;
import com.gancidev.cardmanager.model.Transaction;
import com.gancidev.cardmanager.repository.CardRepository;
import com.gancidev.cardmanager.service.CardService;
import com.gancidev.cardmanager.service.TransactionService;

@RestController
@RequestMapping("transaction")
public class TransactionController extends AbstractController{

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

    /* API per la creazione di una transazione*/
    @PostMapping("/create")
    public ResponseEntity<ResponseToFE> create(@RequestBody TransactionRequest dto) {
        Session sessione = getSession();
        if(sessione!=null && sessione.getPrivileges().equals("admin")){
            Transaction response = this.transactionService.create(dto);
            Card card = this.cardRepository.findById(dto.getCard_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            CardRequest cardDto = new CardRequest();
            cardDto.setNumber(card.getNumber());
            cardDto.setCredit(dto.getCredit());
            this.cardService.updateCredit(cardDto);
            return ResponseEntity.ok(new ResponseToFE(response));
        }
        return ResponseEntity.ok(new ResponseToFE(Boolean.TRUE));
    }


    /* API per il recupero delle transazioni di un negoziante*/
    @PostMapping("/report/myTransaction")
    public ResponseEntity<List<Transaction>> reportPersonalTransaction(@RequestBody TransactionRequest dto) {
        Session sessione = getSession();
        if(sessione!=null && sessione.getPrivileges().equals("admin")){
            return ResponseEntity.ok(this.transactionService.reportPersonalTransaction(dto.getUser_shop_id()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }


    /* API per il recupero delle ultime 10 transazioni di un cliente*/
    @PostMapping("/report/myLastTransaction")
    public ResponseEntity<List<Transaction>> reportMyLastTransaction(@RequestBody UserRequest dto) {
        Session sessione = getSession();
        if(sessione!=null && sessione.getPrivileges().equals("admin")){
            return ResponseEntity.ok(this.transactionService.reportMyLastTransaction(dto.getId()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }
}