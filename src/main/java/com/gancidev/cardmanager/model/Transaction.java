package com.gancidev.cardmanager.model;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "transaction")
public class Transaction {

    private Long transaction_id;
    private Long card_id;
    private Long user_shop_id;
    private Double credit;
    private String type;                //payment oppure accredit
    private LocalDateTime date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getTransaction_id() {
        return transaction_id;
    }

     @Column(nullable = false)
    public Long getCard_id() {
        return card_id;
    }

    @Column(nullable = false)
    public Long getUser_shop_id() {
        return user_shop_id;
    }
    
}