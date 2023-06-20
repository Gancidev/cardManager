package com.gancidev.cardmanager.dto.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {
    private String transaction_id;
    private Long card_id;
    private Long user_shop_id;
    private Double credit;
    private String type;
}