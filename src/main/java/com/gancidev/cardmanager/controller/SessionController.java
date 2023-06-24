package com.gancidev.cardmanager.controller;

import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gancidev.cardmanager.dto.controller.ResponseToFE;
import com.gancidev.cardmanager.dto.controller.UserRequest;
import com.gancidev.cardmanager.model.Session;
import com.gancidev.cardmanager.model.User;

@RestController
@RequestMapping("session")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class SessionController extends AbstractController{


    /* API per il recupero dei dati di un utente*/
    @GetMapping("{token}")
    public Session getByToken(@PathVariable("token") String token) {
        return this.sessionService.getByToken(token);
    }


    /* API per bloccare e sbloccare un utente*/
    @PostMapping("/login")
    public ResponseEntity<ResponseToFE> login(@RequestBody UserRequest dto) {
        Session sessione = null;
        dto.setPassword(DigestUtils.md5Hex(dto.getPassword()));
        Optional<User> user = this.sessionService.checkCredential(dto);
        if(user.isPresent()){
            if(user.get().getDisabled()){
                return ResponseEntity.ok(new ResponseToFE(Boolean.TRUE, "Utenza bloccata, contattare l'amministratore per ulteriori informazioni"));
            }else{
                sessione = this.sessionService.createSession(user);
                return ResponseEntity.ok(new ResponseToFE(sessione));
            }
        }
        else{
            return ResponseEntity.ok(new ResponseToFE(Boolean.TRUE, "Credenziali Errate"));
        }
    }


    /* API per bloccare e sbloccare un utente*/
    @PostMapping("/logout")
    public ResponseEntity<ResponseToFE> logout() {
        Session sessione = getSession();
        return ResponseEntity.ok(new ResponseToFE(this.sessionService.deleteSession(sessione)));
    }
}