package com.gancidev.cardmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gancidev.cardmanager.model.Session;


@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    /* Query per il recupero dei dati di una carta dato il suo numero*/
    Optional<Session> findByToken(String token);

}