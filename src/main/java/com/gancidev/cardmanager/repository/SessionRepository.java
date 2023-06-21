package com.gancidev.cardmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gancidev.cardmanager.model.Session;

import jakarta.transaction.Transactional;


@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    /* Query per il recupero dei dati di una carta dato il suo numero*/
    Optional<Session> findByToken(String token);

    /* Query per accreditare o effettuare un pagamento con una carta dato il suo numero*/
    @Modifying
    @Transactional
    @Query(value="DELETE FROM session WHERE token= :token", nativeQuery = true)
    void deleteSession(@Param("token") String token);

}