package com.example.documentation_center.repositories;

import com.example.documentation_center.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Integer> {

    Page<User> findByNomeContains(String searchTerm, Pageable pageable);
    //Page<User> findByRazaoContains(String searchTerm, Pageable pageable);
    //Page<User> findByEnderecoContains(String searchTerm, Pageable pageable);

    Optional<User> findByNome(String nome);
    Optional<User> findBySenha(String senha);
    //Optional<User> findByEmail(String email);
    //Optional<User> findByCnpj(String cnpj);
    //Optional<User> findByTelefone(String telefone);

}
