package com.gancidev.cardmanager.dto.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    private String transaction_id;
    private Long card_id;
    private Long user_shop_id;
    private Double credit;
    private String type;
}