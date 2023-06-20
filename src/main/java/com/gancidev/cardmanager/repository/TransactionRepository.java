package com.gancidev.cardmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gancidev.cardmanager.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /* Query per il recupero delle transazioni di un negoziante dato il suo identificativo*/
    @Query(value="SELECT * FROM transaction WHERE user_shop_id = :user_shop_id", nativeQuery = true)
    Optional<List<Transaction>> reportById(@Param("user_shop_id") Long user_shop_id);

    /* Query per il recupero delle ultime 10 transazioni di un cliente dato il suo identificativo*/
    @Query(value="SELECT * FROM transaction WHERE card_id IN (SELECT card_id FROM card WHERE user_id = :user_id)  ORDER BY date DESC LIMIT 10", nativeQuery = true)
    Optional<List<Transaction>> reportMyLastTransaction(@Param("user_id") Long user_id);








    
    /* IDEE PER POSSIBILI REPORT PARAMETRIZZATI PER L'ADMIN*/

    /* Query per il recupero delle transazioni con credito superiore ad una soglia*/
    @Query(value="SELECT * FROM transaction WHERE credit > :credit", nativeQuery = true)
    Optional<List<Transaction>> reportTransactionWhitCreditMoreThan(@Param("credit") Double credit);

    /* Query per il recupero delle transazioni con credito superiore ad una soglia*/
    @Query(value="SELECT * FROM transaction WHERE credit < :credit", nativeQuery = true)
    Optional<List<Transaction>> reportTransactionWhitCreditLessThan(@Param("credit") Double credit);

    /* Query per il recupero delle transazioni con credito superiore ad una soglia ed inferiore ad un'altra*/
    @Query(value="SELECT * FROM transaction WHERE credit BETWEEN :credit1 AND :credit2", nativeQuery = true)
    Optional<List<Transaction>> reportTransactionWhitCreditLessThanAndMoreThan(@Param("credit1") Double credit1, @Param("credit2") Double credit2);

    /* Query per il recupero delle transazioni di un cliente dato il suo identificativo*/
    @Query(value="SELECT * FROM transaction WHERE card_id IN (SELECT card_id FROM card WHERE user_id = :user_id)", nativeQuery = true)
    Optional<List<Transaction>> reportClientTransaction(@Param("user_id") Long user_id);

    /* Query per il recupero delle transazioni con credito superiore ad una soglia*/
    @Query(value="SELECT * FROM transaction WHERE date > :date", nativeQuery = true)
    Optional<List<Transaction>> reportTransactionWhitDateMoreThan(@Param("date") String date);

    /* Query per il recupero delle transazioni con credito superiore ad una soglia*/
    @Query(value="SELECT * FROM transaction WHERE date < :date", nativeQuery = true)
    Optional<List<Transaction>> reportTransactionWhitDateLessThan(@Param("date") String date);

    /* Query per il recupero delle transazioni con data creazione superiore ad una soglia ed inferiore ad un'altra*/
    @Query(value="SELECT * FROM transaction WHERE date BETWEEN :date1 AND :date2", nativeQuery = true)
    Optional<List<Transaction>> reportTransactionWhitDateLessThanAndMoreThan(@Param("date1") String date1, @Param("date2") String date2);

    /* Query per il recupero delle transazioni di una carta dato il suo identificativo*/
    @Query(value="SELECT * FROM transaction WHERE card_id = :card_id", nativeQuery = true)
    Optional<List<Transaction>> reportCardTransactions(@Param("card_id") Long card_id);


}