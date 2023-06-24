package com.gancidev.cardmanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gancidev.cardmanager.dto.controller.ResponseToFE;
import com.gancidev.cardmanager.model.Counter;
import com.gancidev.cardmanager.model.Session;
import com.gancidev.cardmanager.service.CardService;
import com.gancidev.cardmanager.service.TransactionService;
import com.gancidev.cardmanager.service.UserService;


@RestController
@RequestMapping("general/")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class CounterController extends AbstractController{

    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;

    @Autowired
    private TransactionService transactionService;
    
    /* API per il recupero dei dati di una carta*/
    @GetMapping("counters")
    public ResponseEntity<ResponseToFE> getCounter() {
        List<Counter> counters = new ArrayList<>();
        Session sessione = getSession();
        if(sessione!=null){
            
            Integer numberOfCards = this.cardService.getNumberOfMyCard(sessione.getUser().getId());
            counters.add(new Counter(numberOfCards));
            
            if(sessione.getPrivileges().equals("admin")){
                Integer numberOfCustomers = this.userService.getNumberOfCustomers();
                Integer numberOfMerchant = this.userService.getNumberOfMerchant();
                Integer numberOfAdmin = this.userService.getNumberOfAdmin();
                counters.add(new Counter(numberOfAdmin, numberOfMerchant, numberOfCustomers));
            }

            Integer numberOfTransactions = this.transactionService.getNumberoOfTransaction(sessione.getUser().getId());
            List<Double> creditOfTransactions = this.transactionService.getCounterOfTransactions(sessione.getUser().getId());
            if(!creditOfTransactions.isEmpty()){
                counters.add(new Counter(creditOfTransactions, numberOfTransactions));
            }
            else{
                counters.add(new Counter());
            }
            return ResponseEntity.ok(new ResponseToFE(counters, null, null, null, null));
        }
        return ResponseEntity.ok(new ResponseToFE(Boolean.TRUE));
    }
}