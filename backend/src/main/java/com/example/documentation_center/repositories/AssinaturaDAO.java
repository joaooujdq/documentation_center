package com.example.documentation_center.repositories;

import com.example.documentation_center.models.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssinaturaDAO extends JpaRepository<Assinatura, Long> {

    List<Assinatura> findByUserObjCodigo(Long userCodigo);

    @Query("SELECT a FROM Assinatura a WHERE a.branchObj.codigo = :branchId")
    List<Assinatura> findByBranch(@Param("branchId") Long branchId);

    @Query("SELECT a FROM Assinatura a WHERE a.folderObj.codigo = :folderId")
    List<Assinatura> findByFolder(@Param("folderId") Long folderId);

    @Query("SELECT COUNT(a) > 0 FROM Assinatura a WHERE a.userObj.codigo = :userId AND a.branchObj.codigo = :branchId")
    boolean existsByUserAndBranch(@Param("userId") Long userId, @Param("branchId") Long branchId);

    @Query("SELECT COUNT(a) > 0 FROM Assinatura a WHERE a.userObj.codigo = :userId AND a.folderObj.codigo = :folderId")
    boolean existsByUserAndFolder(@Param("userId") Long userId, @Param("folderId") Long folderId);
}
