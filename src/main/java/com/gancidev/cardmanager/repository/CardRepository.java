package com.gancidev.cardmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gancidev.cardmanager.model.Card;

import jakarta.transaction.Transactional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    /* Query per il recupero dei dati di una carta dato il suo numero*/
    Optional<Card> findByNumber(String number);

    /* Query per accreditare o effettuare un pagamento con una carta dato il suo numero*/
    @Modifying
    @Transactional
    @Query(value="UPDATE card SET credit= :credit WHERE number = :number", nativeQuery = true)
    void updateCardCredit(@Param("credit")Double credit, @Param("number") String number);

    /* Query per bloccare e sbloccare una carta dato il suo numero*/
    @Modifying
    @Transactional
    @Query(value="UPDATE card SET blocked = IF(blocked = 1, 0, 1) WHERE number = :number", nativeQuery = true)
    void lockUnlockCard(@Param("number") String number);

    /* Query per il recupero del credito di una carta dato il suo numero*/
    @Query(value="SELECT credit FROM card WHERE number = :number", nativeQuery = true)
    Optional<Double> getCardCredit(@Param("number") String number);


    /* Query per il recupero del numero di carte di un cliente dato il suo identificativo*/
    @Query(value="SELECT COUNT(*) FROM card WHERE user_id = :user_id", nativeQuery = true)
    Optional<Integer> numberOfMyCard(@Param("user_id") Long user_id);

    /* Query per il recupero delle carte di un cliente con id di riferimento*/
    @Query(value="SELECT * FROM card where user_id = :user_id", nativeQuery = true)
    Optional<List<Card>> getCardByUserId(@Param("user_id") Long user_id);

    /* Query per il recupero delle carte del sistema*/
    @Query(value="SELECT * FROM card", nativeQuery = true)
    Optional<List<Card>> getAllCard();

    /* Query per eliminare una carta dato il suo numero*/
    @Modifying
    @Transactional
    @Query(value="DELETE FROM card WHERE number = :number", nativeQuery = true)
    void deleteCard(@Param("number") String number);

    /* Query per eliminare tutte le carte di un cliente*/
    @Modifying
    @Transactional
    @Query(value="DELETE FROM card WHERE user_id = :user_id", nativeQuery = true)
    void deleteAllCard(@Param("user_id") Long user_id);
}