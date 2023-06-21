package com.gancidev.cardmanager.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "session")
public class Session {

    private Long id_session;
    @Column(name = "token", length = 16777215)
    private String token;
    @Column(name = "privileges", length = 15)
    private String privileges;
    private User user;
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId_session() {
        return id_session;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

    
}