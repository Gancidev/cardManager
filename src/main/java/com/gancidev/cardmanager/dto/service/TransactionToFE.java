package com.gancidev.cardmanager.dto.service;

import com.gancidev.cardmanager.model.Transaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionToFE extends Transaction{
    private String cardNumber;
    private String customer;
    private String merchant;

    public TransactionToFE(Transaction tmp){
        this.setTransaction_id(tmp.getTransaction_id());
        this.setCard_id(tmp.getCard_id());
        this.setCredit(tmp.getCredit());
        this.setType(tmp.getType());
        this.setDate(tmp.getDate());
    }
}