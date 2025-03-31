package com.example.documentation_center.repositories;

import com.example.documentation_center.models.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FolderDAO extends JpaRepository<Folder, Integer> {

    Page<Folder> findByNomeContains(String searchTerm, Pageable pageable);

    Optional<Folder> findByNome(String nome);
}
