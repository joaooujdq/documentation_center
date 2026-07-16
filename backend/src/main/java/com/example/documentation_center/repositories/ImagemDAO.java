package com.example.documentation_center.repositories;

import com.example.documentation_center.models.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagemDAO extends JpaRepository<Imagem, Long> {
}
