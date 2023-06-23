package com.gancidev.cardmanager.dto.service;

import com.gancidev.cardmanager.model.Card;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CardToFE extends Card{
    private String email;

    public CardToFE(Card tmp){
        this.setCvv(tmp.getCvv());
        this.setExpiration(tmp.getExpiration());
        this.setTransactions(tmp.getTransactions());
        this.setUser_id(tmp.getUser_id());
        this.setBlocked(tmp.getBlocked());
        this.setCard_id(tmp.getCard_id());
        this.setCredit(tmp.getCredit());
        this.setNumber(tmp.getNumber());
    }
}