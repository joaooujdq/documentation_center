package com.example.documentation_center.repositories;

import com.example.documentation_center.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Long>{


    @Query("SELECT u FROM User u WHERE u.nome =:nome")
    User findByNome(@Param("nome") String nome);

    //%AND%
    // Fernanda
    // Alessandra
    @Query("SELECT p FROM User p WHERE p.nome LIKE LOWER(CONCAT ('%',:nome,'%'))")
    Page<User> findUserByNome(@Param("nome") String nome, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.nome =:nome")
    User findUserByNome(@Param("nome") String nome);

    @Modifying
    @Query("UPDATE User p SET p.enabled = false WHERE p.id =:id")
    void disablePerson(@Param("id") Long id);

}
