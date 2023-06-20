package com.gancidev.cardmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gancidev.cardmanager.model.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /* Query per il recupero dei dati di un utente mediante l'email*/
    Optional<User> findByEmail(String email);

    /* Query per bloccare e sbloccare un utente data la sua email*/
    @Modifying
    @Transactional
    @Query(value="UPDATE user SET disabled = IF(disabled = 1, 0, 1) WHERE email = :email", nativeQuery = true)
    void blockUnblockUser(@Param("email") String email);













    
    /* IDEE PER POSSIBILI REPORT PARAMETRIZZATI PER L'ADMIN*/

    /* Query per il recupero degli utenti del ruolo scelto*/
    @Query(value="SELECT * FROM user where role = :role", nativeQuery = true)
    Optional<List<User>> reportUserByRole(@Param("role") String role);

    /* Query per il recupero degli utenti non bloccati o bloccati a seconda del parametro scelto*/
    @Query(value="SELECT * FROM user where disabled = :disabled", nativeQuery = true)
    Optional<List<User>> reportUserByStatus(@Param("disabled") Integer disabled);

    /* Query per il recupero dei crediti totali degli utenti registrati*/
    @Query(value="SELECT user.*, SUM(card.credit) FROM user, card WHERE user.id = card.user_id GROUP BY user.id; ", nativeQuery = true)
    Optional<List<User>> reportUserCredit();
}