package com.example.documentation_center.services;

import com.example.documentation_center.dtos.BranchDTO;
import com.example.documentation_center.models.Branch;
import com.example.documentation_center.models.Folder;
import com.example.documentation_center.models.User;
import com.example.documentation_center.repositories.*;
import com.example.documentation_center.services.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BranchServices {
    private BranchDAO branchDAO;
    private UserDAO userDAO;

    @Transactional(readOnly = true)
    public Page<BranchDTO> findAll(Pageable pageable) {
        Page<Branch> result = branchDAO.findAll(pageable);
        return result.map(obj -> new BranchDTO(obj));
    }

    @Transactional(readOnly = true)
    public BranchDTO findById(Integer id) {
        Branch result = branchDAO.findById(id).orElseThrow(() -> new BusinessException("Registros não encontrados"));
        return new BranchDTO(result);
    }

    @Transactional(readOnly = true)
    public boolean existById(Integer id) {
        return branchDAO.existsById(id);
    }

    /*@Transactional(readOnly = true)
    public BranchDTO findByEmail(String email) {
        Branch result = branchDAO.findByEmail(email).orElseThrow(() -> new BusinessException("Registros não encontrados"));
        return new BranchDTO(result);
    }*/

    @Transactional
    public BranchDTO update(BranchDTO obj) {
        Branch entity = branchDAO.findById(obj.getCodigo()).orElseThrow(() -> new BusinessException("Registros não encontrados!!!"));
        entity.setNome(obj.getNome());
        entity.setDescricao(obj.getDescricao());
        entity.setDataHora(obj.getDataHora());
        return new BranchDTO(branchDAO.save(entity));
    }

    @Transactional(readOnly = true)
    public BranchDTO findByNome(String nome) {
        Branch result = branchDAO.findByNome(nome).orElseThrow(() -> new BusinessException("Registros não encontrados"));
        return new BranchDTO(result);
    }

    @Transactional
    public BranchDTO save(BranchDTO obj) {
        //boolean telefoneExists = branchDAO.findByTelefone(obj.getTelefone()).stream().anyMatch(objResult -> !objResult.equals(obj));
        //boolean emailExists = branchDAO.findByEmail(obj.getEmail()).stream().anyMatch(objResult -> !objResult.equals(obj));
        //boolean cnpjExists = branchDAO.findByCnpj(obj.getCnpj()).stream().anyMatch(objResult -> !objResult.equals(obj));
       /* if(obj.getNome() == "" || obj.getDataHora()== null || obj.getUserDTO()==null ) {
            throw new BusinessException("Os campos com * são obrigatórios!");
        }*/
            /*}else if(obj.getCnpj().length()!=14){
            throw new BusinessException("O CNPJ deve ter 14 dígitos!");
        }else if (telefoneExists) {
            throw new BusinessException("Telefone já existente!");
        } else if (emailExists) {
            throw new BusinessException("Email já existente!");
        } else if (cnpjExists) {
            throw new BusinessException("CNPJ já existente!");
        }
         */
        System.out.println("teste branch + " + obj.getUserDTO().getCodigo());
        Optional<User> user = userDAO.findById(obj.getUserDTO().getCodigo());
        System.out.println(user);
        Branch entity = new Branch(obj.getCodigo(),  obj.getDescricao(), obj.getNome(),
                        new User(user.get().getCodigo(),  user.get().getAdmin(), user.get().getNome(), user.get().getDescricao(), user.get().getSenha()));

            return new BranchDTO(branchDAO.save(entity));
    }

    @Transactional
    public void deleteById(Integer id) {
        branchDAO.deleteById(id);
    }

    public Page<BranchDTO> findByNomeContains(String nome, Pageable pageable) {
        Page<Branch> result = branchDAO.findByNomeContains(nome, pageable);
        return result.map(obj -> new BranchDTO(obj));
    }

    /*public Page<BranchDTO> findByRazaoContains(String razao, Pageable pageable) {
        Page<Branch> result = branchDAO.findByRazaoContains(razao, pageable);
        return result.map(obj -> new BranchDTO(obj));
    }

    public Page<BranchDTO> findByEnderecoContains(String endereco, Pageable pageable) {
        Page<Branch> result = branchDAO.findByEnderecoContains(endereco, pageable);
        return result.map(obj -> new BranchDTO(obj));
    }*/

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public BranchServices(BranchDAO branchDAO, UserDAO userDAO) {
        this.branchDAO = branchDAO;
        this.userDAO = userDAO;
    }
    //</editor-fold>
}
