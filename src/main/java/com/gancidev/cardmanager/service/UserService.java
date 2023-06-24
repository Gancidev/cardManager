package com.gancidev.cardmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.apache.commons.codec.digest.DigestUtils;

import com.gancidev.cardmanager.dto.controller.UserRequest;
import com.gancidev.cardmanager.model.User;
import com.gancidev.cardmanager.repository.CardRepository;
import com.gancidev.cardmanager.repository.TransactionRepository;
import com.gancidev.cardmanager.repository.UserRepository;


@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    
    /* Servizio per il recupero del numero di Admin*/
    public Integer getNumberOfAdmin() {
        return this.userRepository.numberOfUserPerRole("admin").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* Servizio per il recupero del numero di Customers*/
    public Integer getNumberOfCustomers() {
        return this.userRepository.numberOfUserPerRole("cliente").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* Servizio per il recupero del numero di Merchant*/
    public Integer getNumberOfMerchant() {
        return this.userRepository.numberOfUserPerRole("venditore").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* Servizio per il recupero dei clienti con un certo ruolo*/
    public List<User> getListByRole(String role) {
        return this.userRepository.getListByRole(role).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    /* Servizio per la creazione di un utente*/
    public User create(UserRequest dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setPassword(DigestUtils.md5Hex(dto.getPassword()));
        user.setRole(dto.getRole());
        user.setDisabled(Boolean.FALSE);
        try {
            return this.userRepository.save(user);
        } catch (Exception e) {
            if(e.getMessage().contains("Duplicate entry")){
                return new User();
            }
            return null;
        }
    }

    /* Servizio per la cancellazione della sessione*/
    public Boolean deleteUser(UserRequest dto) {
        try {
            this.transactionRepository.deleteAllTransaction(dto.getId());
            this.transactionRepository.deleteAllMerchantTransaction(dto.getId());
            this.cardRepository.deleteAllCard(dto.getId());
            this.userRepository.deleteUser(dto.getId());
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.TRUE;
        }
    }
    
    /* Servizio per bloccare e sbloccare un utente*/
    public User blockUnblockUser(UserRequest dto) {
        this.userRepository.blockUnblockUser(dto.getEmail());
        return this.userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}