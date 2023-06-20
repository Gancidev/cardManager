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
@Table(name = "user")
public class User {

    private Long id;
    private String name;
    private String surname;
    private String email;
    @Column(name = "password", length = 65535)
    private String password;
    private String role;                            //Role può essere: cliente, negoziante, admin
    private Boolean disabled;                            //Il negoziante può essere disabilitato dall'admin
    private Set<Card> cards = new HashSet<>();
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    @OneToMany
	@JoinColumn(name="user_id")
    public Set<Card> getCards() {
		return cards;
	}
	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}
}