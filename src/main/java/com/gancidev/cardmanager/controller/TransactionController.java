package com.gancidev.cardmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gancidev.cardmanager.dto.service.CardDto;
import com.gancidev.cardmanager.dto.service.TransactionDto;
import com.gancidev.cardmanager.dto.service.UserDto;
import com.gancidev.cardmanager.model.Card;
import com.gancidev.cardmanager.model.Transaction;
import com.gancidev.cardmanager.repository.CardRepository;
import com.gancidev.cardmanager.service.CardService;
import com.gancidev.cardmanager.service.TransactionService;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

    /* API per il recupero dei dati di una transazione*/
    @GetMapping("{transaction_id}")
    public Transaction getByTransaction_id(@PathVariable("transaction_id") Long transaction_id) {
        return this.transactionService.getByTransaction_id(transaction_id);
    }

    /* API per la creazione di una transazione*/
    @PostMapping("/create")
    public Transaction create(@RequestBody TransactionDto dto) {
        Transaction response = this.transactionService.create(dto);
        Card card = this.cardRepository.findById(dto.getCard_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        CardDto cardDto = new CardDto();
        cardDto.setNumber(card.getNumber());
        cardDto.setCredit(dto.getCredit());
        this.cardService.updateCredit(cardDto);
        return response;
    }


    /* API per il recupero delle transazioni di un negoziante*/
    @PostMapping("/report/myTransaction")
    public List<Transaction> reportPersonalTransaction(@RequestBody TransactionDto dto) {
        return this.transactionService.reportPersonalTransaction(dto.getUser_shop_id());
    }


    /* API per il recupero delle ultime 10 transazioni di un cliente*/
    @PostMapping("/report/myLastTransaction")
    public List<Transaction> reportMyLastTransaction(@RequestBody UserDto dto) {
        return this.transactionService.reportMyLastTransaction(dto.getId());
    }
}