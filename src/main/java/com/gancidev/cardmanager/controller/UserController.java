package com.gancidev.cardmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gancidev.cardmanager.dto.controller.ResponseToFE;
import com.gancidev.cardmanager.dto.controller.UserRequest;
import com.gancidev.cardmanager.model.Session;
import com.gancidev.cardmanager.model.User;
import com.gancidev.cardmanager.service.UserService;


@RestController
@RequestMapping("user")
public class UserController extends AbstractController{

    @Autowired
    private UserService userService;
    
    /* API per la creazione di un utente*/
    @PostMapping("/create")
    public ResponseEntity<ResponseToFE> create(@RequestBody UserRequest dto) {
        Session sessione = getSession();
        if(sessione!=null && sessione.getPrivileges().equals("admin")){
            User user = this.userService.create(dto);
            if(user.getId()!=null){
                return ResponseEntity.ok(new ResponseToFE(user));
            }
            return ResponseEntity.ok(new ResponseToFE(Boolean.TRUE, "Email già utilizzata"));
        }
        return ResponseEntity.ok(new ResponseToFE(Boolean.TRUE));
    }

    
    /* API per bloccare e sbloccare un utente*/
    @PostMapping("/blockUnblock")
    public ResponseEntity<ResponseToFE> blockUnblock(@RequestBody UserRequest dto) {
        Session sessione = getSession();
        if(sessione!=null && sessione.getPrivileges().equals("admin")){
            return ResponseEntity.ok(new ResponseToFE(this.userService.blockUnblockUser(dto)));
        }
        return ResponseEntity.ok(new ResponseToFE(Boolean.TRUE, "Errore si prega di riprovare"));
    }
}