package com.example.documentation_center.services;

import com.example.documentation_center.dtos.UserDTO;
import com.example.documentation_center.models.User;
import com.example.documentation_center.repositories.UserDAO;
import com.example.documentation_center.services.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServices {
    private UserDAO userDAO;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> result = userDAO.findAll(pageable);
        return result.map(obj -> new UserDTO(obj));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Integer id) {
        System.out.println("teste final");
        User result = userDAO.findById(id).orElseThrow(() -> new BusinessException("Registros não encontrados"));
        return new UserDTO(result);
    }

    @Transactional(readOnly = true)
    public boolean existById(Integer id) {
        return userDAO.existsById(id);
    }

    /*@Transactional(readOnly = true)
    public UserDTO findByEmail(String email) {
        User result = userDAO.findByEmail(email).orElseThrow(() -> new BusinessException("Registros não encontrados"));
        return new UserDTO(result);
    }*/

    @Transactional
    public UserDTO update(UserDTO obj) {
        User entity = userDAO.findById(obj.getCodigo()).orElseThrow(() -> new BusinessException("Registros não encontrados!!!"));
        entity.setNome(obj.getNome());
        entity.setSenha(obj.getSenha());
        entity.setAdmin(obj.getAdmin());
        entity.setDataHora(obj.getDataHora());
        return new UserDTO(userDAO.save(entity));
    }

    @Transactional(readOnly = true)
    public UserDTO findByNome(String nome) {
        User result = userDAO.findByNome(nome).orElseThrow(() -> new BusinessException("Registros não encontrados"));
        return new UserDTO(result);
    }

    @Transactional(readOnly = true)
    public UserDTO findBySenha(String senha) { 
        User result = userDAO.findBySenha(senha).orElseThrow(() -> new BusinessException("Registros não encontrados"));
        return new UserDTO(result);
    }

    @Transactional
    public UserDTO save(UserDTO obj) {

        if(obj.getNome()=="" || obj.getSenha()=="" ) {
            throw new BusinessException("Os campos com * são obrigatórios!");
        }

        User entity = new User(obj.getCodigo() ,obj.getAdmin(), obj.getNome(), obj.getDescricao(), obj.getSenha());
        return new UserDTO(userDAO.save(entity));
    }

    @Transactional
    public void deleteById(Integer id) {
        userDAO.deleteById(id);
    }

    public Page<UserDTO> findByNomeContains(String nome, Pageable pageable) {
        Page<User> result = userDAO.findByNomeContains(nome, pageable);
        return result.map(obj -> new UserDTO(obj));
    }

    /*public Page<UserDTO> findByRazaoContains(String razao, Pageable pageable) {
        Page<User> result = userDAO.findByRazaoContains(razao, pageable);
        return result.map(obj -> new UserDTO(obj));
    }

    public Page<UserDTO> findByEnderecoContains(String endereco, Pageable pageable) {
        Page<User> result = userDAO.findByEnderecoContains(endereco, pageable);
        return result.map(obj -> new UserDTO(obj));
    }*/

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public UserServices(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    //</editor-fold>
}
