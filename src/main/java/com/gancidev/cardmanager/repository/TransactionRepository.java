package com.gancidev.cardmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gancidev.cardmanager.model.Transaction;

import jakarta.transaction.Transactional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    /* Query per il recupero delle somme delle transazioni di un negoziante dato il suo identificativo divise per tipo*/
    @Query(value="SELECT SUM(credit) FROM transaction WHERE card_id IN (SELECT card_id FROM card WHERE user_id = :user_id) GROUP BY type;", nativeQuery = true)
    Optional<List<Double>> getCounter(@Param("user_id") Long user_id);

    /* Query per il recupero delle transazioni di un negoziante dato il suo identificativo*/
    @Query(value="SELECT * FROM transaction WHERE user_shop_id = :user_shop_id", nativeQuery = true)
    Optional<List<Transaction>> reportById(@Param("user_shop_id") Long user_shop_id);

    /* Query per il recupero del numero di transazioni di un cliente dato il suo identificativo*/
    @Query(value="SELECT COUNT(*) FROM transaction WHERE card_id IN (SELECT card_id FROM card WHERE user_id = :user_id)  ORDER BY date DESC", nativeQuery = true)
    Optional<Integer> numberOfMyTransaction(@Param("user_id") Long user_id);

    /* Query per il recupero delle ultime 10 transazioni di un cliente dato il suo identificativo*/
    @Query(value="SELECT * FROM transaction WHERE card_id IN (SELECT card_id FROM card WHERE user_id = :user_id)  ORDER BY date DESC LIMIT 10", nativeQuery = true)
    Optional<List<Transaction>> reportMyLastTransaction(@Param("user_id") Long user_id);

    /* Query per il recupero delle ultime 10 transazioni di un cliente dato il suo identificativo*/
    @Query(value="SELECT * FROM transaction WHERE card_id IN (SELECT card_id FROM card WHERE user_id = :user_id)  ORDER BY date", nativeQuery = true)
    Optional<List<Transaction>> reportMyTransaction(@Param("user_id") Long user_id);

    /* Query per eliminare le transizioni di una carta dato il suo numero*/
    @Modifying
    @Transactional
    @Query(value="DELETE FROM transaction WHERE card_id IN (SELECT card_id FROM card WHERE number= :number)", nativeQuery = true)
    void deleteTransaction(@Param("number") String number);

    /* Query per eliminare tutte le transizioni di tutte le carte di un utente*/
    @Modifying
    @Transactional
    @Query(value="DELETE FROM transaction WHERE card_id IN (SELECT card_id FROM card WHERE user_id = :user_id)", nativeQuery = true)
    void deleteAllTransaction(@Param("user_id") Long user_id);

    /* Query per eliminare tutte le transizioni di un venditore*/
    @Modifying
    @Transactional
    @Query(value="DELETE FROM transaction WHERE user_shop_id = :user_shop_id", nativeQuery = true)
    void deleteAllMerchantTransaction(@Param("user_shop_id") Long user_shop_id);
}