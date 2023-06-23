package com.gancidev.cardmanager.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gancidev.cardmanager.Enum.TypeEnum;
import com.gancidev.cardmanager.dto.controller.TransactionRequest;
import com.gancidev.cardmanager.dto.service.TransactionToFE;
import com.gancidev.cardmanager.model.Card;
import com.gancidev.cardmanager.model.Transaction;
import com.gancidev.cardmanager.model.User;
import com.gancidev.cardmanager.repository.CardRepository;
import com.gancidev.cardmanager.repository.TransactionRepository;
import com.gancidev.cardmanager.repository.UserRepository;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

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
    public List<TransactionToFE> reportPersonalTransaction(Long user_shop_id) {
        List<Transaction> listTransactions =  this.transactionRepository.reportById(user_shop_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<TransactionToFE> listTransactionsCompleted = new ArrayList<>();
        listTransactions.forEach(transaction->{
            TransactionToFE tmp = new TransactionToFE(transaction);
            Card tmpCard = this.cardRepository.findById(transaction.getCard_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            User tmpCustomer = this.userRepository.findById(tmpCard.getUser_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            User tmpMerchant = this.userRepository.findById(transaction.getUser_shop_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            tmp.setCardNumber(tmpCard.getNumber());
            tmp.setCustomer(StringUtils.join(tmpCustomer.getName(), " ", tmpCustomer.getSurname()));
            tmp.setMerchant(StringUtils.join(tmpMerchant.getName(), " ", tmpMerchant.getSurname()));
            listTransactionsCompleted.add(tmp);
        });
        return listTransactionsCompleted;
    }

    /* Servizio per il recupero del numero transazioni di un cliente*/
    public Integer getNumberoOfTransaction(Long user_id) {
        return this.transactionRepository.numberOfMyTransaction(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* Servizio per il recupero delle transazioni di un cliente*/
    public List<TransactionToFE> reportMyTransaction(Long user_id) {
        List<TransactionToFE> listTransactionsCompleted = new ArrayList<>();
        List<Transaction> listTransactions = this.transactionRepository.reportMyTransaction(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        listTransactions.forEach(transaction->{
            TransactionToFE tmp = new TransactionToFE(transaction);
            Card tmpCard = this.cardRepository.findById(transaction.getCard_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            User tmpUser = this.userRepository.findById(tmpCard.getUser_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            tmp.setCardNumber(tmpCard.getNumber());
            tmp.setCustomer(StringUtils.join(tmpUser.getName(), " ", tmpUser.getSurname()));
            listTransactionsCompleted.add(tmp);
        });
        return listTransactionsCompleted;
    }
}