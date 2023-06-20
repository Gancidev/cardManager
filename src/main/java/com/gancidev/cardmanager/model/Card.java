package com.gancidev.cardmanager.model;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "card")
public class Card {

    private Long card_id;
    @Column(name = "number", length = 16)
    private String number;
    private Double credit;
    private Long user_id;
    private Boolean blocked;
    private Set<Transaction> transactions = new HashSet<>();
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCard_id() {
        return card_id;
    }

    @Column(nullable = false, unique = true)
    public String getNumber() {
        return number;
    }

    @Column(nullable = false)
    public Long getUser_id() {
        return user_id;
    }

    @OneToMany
	@JoinColumn(name="card_id")
    public Set<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}
    
}