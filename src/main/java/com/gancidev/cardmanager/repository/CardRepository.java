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








    

    /* IDEE PER POSSIBILI REPORT PARAMETRIZZATI PER L'ADMIN*/

    /* Query per il recupero delle carte registrate non bloccate o bloccate a seconda del parametro scelto*/
    @Query(value="SELECT * FROM card where blocked = :blocked", nativeQuery = true)
    Optional<List<Card>> reportCardByStatus(@Param("blocked") Integer blocked);

    /* Query per il recupero delle carte di un cliente con id di riferimento*/
    @Query(value="SELECT * FROM card where user_id = :user_id", nativeQuery = true)
    Optional<List<Card>> reportCardByUserId(@Param("user_id") Long user_id);

    /* Query per il recupero delle carte con credito superiore ad una soglia*/
    @Query(value="SELECT * FROM card WHERE credit > :credit", nativeQuery = true)
    Optional<List<Card>> reportCardWhitCreditMoreThan(@Param("credit") Double credit);

    /* Query per il recupero delle carte con credito superiore ad una soglia*/
    @Query(value="SELECT * FROM card WHERE credit < :credit", nativeQuery = true)
    Optional<List<Card>> reportCardWhitCreditLessThan(@Param("credit") Double credit);

    /* Query per il recupero delle carte con credito superiore ad una soglia*/
    @Query(value="SELECT * FROM card WHERE credit BETWEEN :credit1 AND :credit2", nativeQuery = true)
    Optional<List<Card>> reportTransactionWhitCreditLessThanAndMoreThan(@Param("credit1") Double credit1, @Param("credit2") Double credit2);
}