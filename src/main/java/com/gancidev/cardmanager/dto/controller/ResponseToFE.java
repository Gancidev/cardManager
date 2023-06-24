package com.gancidev.cardmanager.dto.controller;

import java.util.List;

import com.gancidev.cardmanager.dto.service.CardToFE;
import com.gancidev.cardmanager.model.Card;
import com.gancidev.cardmanager.model.Counter;
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
    private List<Counter> counters;
    private List<CardToFE> cards;
    private List<User> customers;
    private List<User> merchants;
    private List<User> admin;


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

    public ResponseToFE(List<Counter> counters, List<CardToFE> cards, List<User> customers, List<User> merchants, List<User> admin){
        if(counters!=null){
            this.counters = counters;
        }
        if(cards!=null){
            this.cards = cards;
        }
        if(customers!=null){
            this.customers = customers;
        }
        if(merchants!=null){
            this.merchants = merchants;
        }
        if(admin!=null){
            this.admin = admin;
        }
    }
}