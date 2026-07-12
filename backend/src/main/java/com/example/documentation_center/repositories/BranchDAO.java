package com.example.documentation_center.repositories;

import com.example.documentation_center.models.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchDAO extends JpaRepository<Branch, Long> {

    @Query("SELECT u FROM Branch u WHERE u.descricao =:descricao")
    Branch findBranchByNome(@Param("descricao") String descricao);

    // @Modifying
    // @Query("UPDATE Person p SET p.enabled = false WHERE p.id =:id")
    //void disablePerson(@Param("id") Long id);

    @Query("SELECT p FROM Branch p WHERE p.descricao LIKE LOWER(CONCAT ('%',:descricao,'%'))")
    Page<Branch> findBranchByNome(@Param("descricao") String descricao, Pageable pageable);
}
