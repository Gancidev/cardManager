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


    /* Query per il recupero dei dati di un utente dati email e password*/
    @Query(value="SELECT * FROM user WHERE email = :email AND password = :password", nativeQuery = true)
    Optional<User> checkCredential(@Param("email") String email, @Param("password") String password);

    /* Query per bloccare e sbloccare un utente data la sua email*/
    @Modifying
    @Transactional
    @Query(value="UPDATE user SET disabled = IF(disabled = 1, 0, 1) WHERE email = :email", nativeQuery = true)
    void blockUnblockUser(@Param("email") String email);


    /* Query per il recupero del numero di utenti per ruolo*/
    @Query(value="SELECT COUNT(*) FROM user WHERE role = :role", nativeQuery = true)
    Optional<Integer> numberOfUserPerRole(@Param("role") String role);

    /* Query per il recupero del numero di utenti per ruolo*/
    @Query(value="SELECT * FROM user WHERE role = :role", nativeQuery = true)
    Optional<List<User>> getListByRole(@Param("role") String role);

    /* Query per eliminare un'utente*/
    @Modifying
    @Transactional
    @Query(value="DELETE FROM user WHERE id = :id", nativeQuery = true)
    void deleteUser(@Param("id") Long id);

}