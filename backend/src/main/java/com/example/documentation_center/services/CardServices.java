package com.example.documentation_center.services;

import com.example.documentation_center.dtos.CardDTO;
import com.example.documentation_center.models.Branch;
import com.example.documentation_center.models.Card;
import com.example.documentation_center.models.Folder;
import com.example.documentation_center.models.User;
import com.example.documentation_center.repositories.BranchDAO;
import com.example.documentation_center.repositories.CardDAO;
import com.example.documentation_center.repositories.FolderDAO;
import com.example.documentation_center.repositories.UserDAO;
import com.example.documentation_center.services.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CardServices {
    private CardDAO cardDAO;
    private BranchDAO branchDAO;
    private FolderDAO folderDAO;
    private UserDAO userDAO;

    @Transactional
    public CardDTO save(CardDTO obj) {
        if(obj.getNome()=="" ){
            throw new BusinessException("Os campos com * são obrigatórios!");
        }
        Optional<Folder> folder = folderDAO.findById(obj.getFolderDTO().getCodigo());
        Card entity = new Card(obj.getCodigo() ,obj.getThumbnail(), obj.getImageLink(), obj.getDescricao(), obj.getNome(),
                new Folder(folder.get().getCodigo(), folder.get().getDescricao(), folder.get().getNome(),
                        new Branch(folder.get().getBranchObj().getCodigo(), folder.get().getBranchObj().getDescricao(), folder.get().getBranchObj().getNome(),
                                new User(folder.get().getBranchObj().getUserObj().getCodigo(), folder.get().getBranchObj().getUserObj().getAdmin(), folder.get().getBranchObj().getUserObj().getNome(), folder.get().getBranchObj().getUserObj().getDescricao(), folder.get().getBranchObj().getUserObj().getSenha()))));
        return new CardDTO(cardDAO.save(entity));
    }

    @Transactional(readOnly = true)
    public Page<CardDTO> findAll(Pageable pageable) {
        Page<Card> result = cardDAO.findAll(pageable);
        return result.map(obj -> new CardDTO(obj));
    }

    @Transactional(readOnly = true)
    public CardDTO findById(Integer id) {
        Card result = cardDAO.findById(id).orElseThrow(() -> new BusinessException("Registros não encontrados"));
        return new CardDTO(result);
    }

    @Transactional(readOnly = true)
    public boolean existById(Integer id) {
        return cardDAO.existsById(id);
    }

    /*@Transactional(readOnly = true)
    public CardDTO findByEmail(String email) {
        Card result = cardDAO.findByEmail(email).orElseThrow(() -> new BusinessException("Registros não encontrados"));
        return new CardDTO(result);
    }*/

    @Transactional
    public CardDTO update(CardDTO obj) {
        Card entity = cardDAO.findById(obj.getCodigo()).orElseThrow(() -> new BusinessException("Registros não encontrados!!!"));
        entity.setNome(obj.getNome());
        entity.setDescricao(obj.getDescricao());
        entity.setImageLink(obj.getImageLink());
        entity.setThumbnail(obj.getThumbnail());
        entity.setDataHora(obj.getDataHora());
        return new CardDTO(cardDAO.save(entity));
    }

    @Transactional(readOnly = true)
    public CardDTO findByNome(String nome) {
        Card result = cardDAO.findByNome(nome).orElseThrow(() -> new BusinessException("Registros não encontrados"));
        return new CardDTO(result);
    }

    @Transactional
    public void deleteById(Integer id) {
        cardDAO.deleteById(id);
    }

    public Page<CardDTO> findByNomeContains(String nome, Pageable pageable) {
        Page<Card> result = cardDAO.findByNomeContains(nome, pageable);
        return result.map(obj -> new CardDTO(obj));
    }

    /*public Page<CardDTO> findByRazaoContains(String razao, Pageable pageable) {
        Page<Card> result = cardDAO.findByRazaoContains(razao, pageable);
        return result.map(obj -> new CardDTO(obj));
    }

    public Page<CardDTO> findByEnderecoContains(String endereco, Pageable pageable) {
        Page<Card> result = cardDAO.findByEnderecoContains(endereco, pageable);
        return result.map(obj -> new CardDTO(obj));
    }*/

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public CardServices(CardDAO cardDAO, FolderDAO folderDAO, BranchDAO branchDAO, UserDAO userDAO) {
        this.cardDAO = cardDAO;
        this.folderDAO = folderDAO;
        this.branchDAO = branchDAO;
        this.userDAO = userDAO;
    }
    //</editor-fold>
}
