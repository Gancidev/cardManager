package com.gancidev.cardmanager.dto.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRequest {

    private String number;
    private Double credit;
    private Long user_id;
    private String expiration;
    private String cvv;
}