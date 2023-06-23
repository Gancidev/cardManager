package com.gancidev.cardmanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gancidev.cardmanager.dto.controller.CardRequest;
import com.gancidev.cardmanager.dto.controller.ResponseToFE;
import com.gancidev.cardmanager.dto.controller.TransactionRequest;
import com.gancidev.cardmanager.model.Card;
import com.gancidev.cardmanager.model.Session;
import com.gancidev.cardmanager.model.Transaction;
import com.gancidev.cardmanager.repository.CardRepository;
import com.gancidev.cardmanager.service.CardService;
import com.gancidev.cardmanager.service.TransactionService;

@RestController
@RequestMapping("transaction")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
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
        if(sessione!=null && (sessione.getPrivileges().equals("admin") || sessione.getPrivileges().equals("venditore"))){
            dto.setUser_shop_id(sessione.getUser().getId());
            Card card = this.cardRepository.findByNumber(dto.getCard_number()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            CardRequest cardDto = new CardRequest();
            cardDto.setNumber(dto.getCard_number());
            cardDto.setCredit(dto.getCredit());
            this.cardService.updateCredit(cardDto);
            dto.setCard_id(card.getCard_id());
            Transaction response = this.transactionService.create(dto);
            return ResponseEntity.ok(new ResponseToFE(response));
        }
        return ResponseEntity.ok(new ResponseToFE(Boolean.TRUE));
    }


    /* API per il recupero delle transazioni di un negoziante*/
    @GetMapping("/list")
    public ResponseEntity<List<Transaction>> reportPersonalTransaction() {
        Session sessione = getSession();
        if(sessione!=null){
            List<Transaction> listTransactions = new ArrayList<>();
            if(sessione.getPrivileges().equals("admin") || sessione.getPrivileges().equals("venditore")){
                //Transazioni di cui è l'owner ovvero le ha create lui
                listTransactions = this.transactionService.reportPersonalTransaction(sessione.getUser().getId());
            }
            //Transazioni di cui è il customer
            listTransactions.addAll(this.transactionService.reportMyTransaction(sessione.getUser().getId()));
            return ResponseEntity.ok(listTransactions);
        }
        return ResponseEntity.ok(new ArrayList<>());
    }
}