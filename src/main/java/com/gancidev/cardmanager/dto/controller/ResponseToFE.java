package com.gancidev.cardmanager.dto.controller;

import com.gancidev.cardmanager.model.Card;
import com.gancidev.cardmanager.model.Session;
import com.gancidev.cardmanager.model.Transaction;
import com.gancidev.cardmanager.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseToFE {
    private User user;
    private Card card;
    private Transaction transaction;
    private Session session;
    private Boolean error = Boolean.FALSE;
    private String errorMessage;


    public ResponseToFE(User user){
        this.user = user;
    }

    public ResponseToFE(Card card){
        this.card = card;
    }

    public ResponseToFE(Transaction transaction){
        this.transaction = transaction;
    }

    public ResponseToFE(Session session){
        this.session = session;
    }

    public ResponseToFE(Boolean error){
        this.error = error;
    }

    public ResponseToFE(Boolean error, String errorMessage){
        this.error = error;
        this.errorMessage = errorMessage;
    }
}