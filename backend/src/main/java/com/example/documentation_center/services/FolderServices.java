package com.example.documentation_center.services;

import com.example.documentation_center.dtos.FolderDTO;
import com.example.documentation_center.models.*;
import com.example.documentation_center.repositories.BranchDAO;
import com.example.documentation_center.repositories.FolderDAO;
import com.example.documentation_center.repositories.UserDAO;
import com.example.documentation_center.services.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FolderServices {
    private FolderDAO folderDAO;
    private BranchDAO branchDAO;
    private UserDAO userDAO;

    @Transactional(readOnly = true)
    public Page<FolderDTO> findAll(Pageable pageable) {
        Page<Folder> result = folderDAO.findAll(pageable);
        return result.map(obj -> new FolderDTO(obj));
    }

    @Transactional(readOnly = true)
    public FolderDTO findById(Integer id) {
        Folder result = folderDAO.findById(id).orElseThrow(() -> new BusinessException("Registros não encontrados"));
        return new FolderDTO(result);
    }

    @Transactional(readOnly = true)
    public boolean existById(Integer id) {
        return folderDAO.existsById(id);
    }

    /*@Transactional(readOnly = true)
    public FolderDTO findByEmail(String email) {
        Folder result = folderDAO.findByEmail(email).orElseThrow(() -> new BusinessException("Registros não encontrados"));
        return new FolderDTO(result);
    }*/

    @Transactional
    public FolderDTO update(FolderDTO obj) {
        Folder entity = folderDAO.findById(obj.getCodigo()).orElseThrow(() -> new BusinessException("Registros não encontrados!!!"));
        entity.setNome(obj.getNome());
        entity.setDescricao(obj.getDescricao());
        entity.setDataHora(obj.getDataHora());
        return new FolderDTO(folderDAO.save(entity));
    }

    @Transactional(readOnly = true)
    public FolderDTO findByNome(String nome) {
        Folder result = folderDAO.findByNome(nome).orElseThrow(() -> new BusinessException("Registros não encontrados"));
        return new FolderDTO(result);
    }

    @Transactional
    public FolderDTO save(FolderDTO obj) {
        //boolean telefoneExists = folderDAO.findByTelefone(obj.getTelefone()).stream().anyMatch(objResult -> !objResult.equals(obj));
        //boolean emailExists = folderDAO.findByEmail(obj.getEmail()).stream().anyMatch(objResult -> !objResult.equals(obj));
        //boolean cnpjExists = folderDAO.findByCnpj(obj.getCnpj()).stream().anyMatch(objResult -> !objResult.equals(obj));
       if(obj.getNome()==""  ) {
            throw new BusinessException("Os campos com * são obrigatórios!");
        }

        Optional<Branch> branch = branchDAO.findById(obj.getBranchDTO().getCodigo());
        //Optional<User> user = userDAO.findById(obj.getUserDTO().getCodigo());
        Folder entity = new Folder(obj.getCodigo() , obj.getDescricao(), obj.getNome(),
                new Branch(branch.get().getCodigo(), branch.get().getDescricao(), branch.get().getNome(),
                        new User(branch.get().getUserObj().getCodigo(), branch.get().getUserObj().getAdmin(), branch.get().getUserObj().getNome(), branch.get().getUserObj().getDescricao(), branch.get().getUserObj().getSenha())));
        return new FolderDTO(folderDAO.save(entity));
    }

    @Transactional
    public void deleteById(Integer id) {
        folderDAO.deleteById(id);
    }

    public Page<FolderDTO> findByNomeContains(String nome, Pageable pageable) {
        Page<Folder> result = folderDAO.findByNomeContains(nome, pageable);
        return result.map(obj -> new FolderDTO(obj));
    }

    /*public Page<FolderDTO> findByRazaoContains(String razao, Pageable pageable) {
        Page<Folder> result = folderDAO.findByRazaoContains(razao, pageable);
        return result.map(obj -> new FolderDTO(obj));
    }

    public Page<FolderDTO> findByEnderecoContains(String endereco, Pageable pageable) {
        Page<Folder> result = folderDAO.findByEnderecoContains(endereco, pageable);
        return result.map(obj -> new FolderDTO(obj));
    }*/

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public FolderServices(FolderDAO folderDAO, BranchDAO branchDAO, UserDAO userDAO) {
        this.folderDAO = folderDAO;
        this.branchDAO = branchDAO;
        this.userDAO = userDAO;
    }
    //</editor-fold>
}
