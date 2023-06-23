package com.gancidev.cardmanager.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gancidev.cardmanager.Enum.TypeEnum;
import com.gancidev.cardmanager.dto.controller.TransactionRequest;
import com.gancidev.cardmanager.model.Transaction;
import com.gancidev.cardmanager.repository.TransactionRepository;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    /* Servizio per la creazione di una transazione*/
    public Transaction create(TransactionRequest dto) {
        Transaction transaction = new Transaction();
        transaction.setCard_id(dto.getCard_id());
        transaction.setCredit(dto.getCredit());
        transaction.setUser_shop_id(dto.getUser_shop_id());
        transaction.setType(dto.getCredit()>0 ? TypeEnum.ACCREDIT.getType() : TypeEnum.PAYMENT.getType());
        transaction.setDate(LocalDateTime.now());
        try {
            return this.transactionRepository.save(transaction);
        } catch (Exception e) {
            return null;
        }
    }

    /* Servizio per il recupero del numero delle transazioni totali*/
    public List<Double> getCounterOfTransactions(Long user_id) {
        return this.transactionRepository.getCounter(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* Servizio per il recupero delle transazioni di un negoziante*/
    public List<Transaction> reportPersonalTransaction(Long user_shop_id) {
        return this.transactionRepository.reportById(user_shop_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* Servizio per il recupero del numero transazioni di un cliente*/
    public Integer getNumberoOfTransaction(Long user_id) {
        return this.transactionRepository.numberOfMyTransaction(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* Servizio per il recupero delle transazioni di un cliente*/
    public List<Transaction> reportMyTransaction(Long user_id) {
        return this.transactionRepository.reportMyTransaction(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}