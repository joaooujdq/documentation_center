package com.example.documentation_center.repositories;

import com.example.documentation_center.models.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchDAO extends JpaRepository<Branch, Integer> {

    Page<Branch> findByNomeContains(String searchTerm, Pageable pageable);
    //Page<Branch> findByRazaoContains(String searchTerm, Pageable pageable);
    //Page<Branch> findByEnderecoContains(String searchTerm, Pageable pageable);

    Optional<Branch> findByNome(String nome);
    //Optional<Branch> findByEmail(String email);
    //Optional<Branch> findByCnpj(String cnpj);
    //Optional<Branch> findByTelefone(String telefone);

}
