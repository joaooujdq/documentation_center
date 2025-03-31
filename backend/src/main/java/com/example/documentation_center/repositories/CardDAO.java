package com.example.documentation_center.repositories;

import com.example.documentation_center.models.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardDAO extends JpaRepository<Card, Integer> {

    Page<Card> findByNomeContains(String searchTerm, Pageable pageable);
    //Page<Card> findByRazaoContains(String searchTerm, Pageable pageable);
    //Page<Card> findByEnderecoContains(String searchTerm, Pageable pageable);

    Optional<Card> findByNome(String nome);
    //Optional<Card> findByEmail(String email);
    //Optional<Card> findByCnpj(String cnpj);
    //Optional<Card> findByTelefone(String telefone);

}
