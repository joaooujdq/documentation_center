package com.example.documentation_center.services;

import com.example.documentation_center.converter.DozerConverter;
import com.example.documentation_center.dtos.CardDTO;
import com.example.documentation_center.exception.ResourceNotFoundException;
import com.example.documentation_center.models.Branch;
import com.example.documentation_center.models.Card;
import com.example.documentation_center.models.Folder;
import com.example.documentation_center.models.User;
import com.example.documentation_center.repositories.BranchDAO;
import com.example.documentation_center.repositories.CardDAO;
import com.example.documentation_center.repositories.FolderDAO;
import com.example.documentation_center.repositories.UserDAO;
import com.example.documentation_center.services.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CardServices {
    @Autowired
    CardDAO cardDAO;

    public CardDTO create(CardDTO cardDTO) {
        var entity = DozerConverter.parseObject(cardDTO, Card.class);
        return DozerConverter.parseObject(cardDAO.save(entity), CardDTO.class);
    }

    public Page<CardDTO> findCardByName(String name, Pageable pageable) {
        var page = cardDAO.findCardByNome(name, pageable);
        return page.map(this::convertToCardDTO);
    }

    public CardDTO findCardByName(String name) {
        var entity = cardDAO.findCardByNome(name);
        if (entity != null) {
            return DozerConverter.parseObject(entity, CardDTO.class);
        } else {
            throw new ResourceNotFoundException("Card " + name + " not found!");
        }
    }

    public Page<CardDTO> findAll(Pageable pageable) {
        var page = cardDAO.findAll(pageable);
        return page.map(this::convertToCardDTO);
    }

    private CardDTO convertToCardDTO(Card entity) {
        return DozerConverter.parseObject(entity, CardDTO.class);
    }

    public CardDTO findById(Long id) {
        var entity = cardDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        return DozerConverter.parseObject(entity, CardDTO.class);
    }

    public CardDTO update(CardDTO card) {
        var entity = cardDAO.findById(card.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        //entity.setId(card.getKey());
        entity.setIdBranch(card.getIdBranch());
        entity.setIdFolder(card.getIdFolder());
        entity.setIdUser(card.getIdUser());
        entity.setNome(card.getNome());
        entity.setDescricao(card.getDescricao());
        entity.setThumbnail(card.getThumbnail());
        entity.setImageLink(card.getImageLink());
        entity.setDataHora(card.getDataHora());

        return DozerConverter.parseObject(cardDAO.save(entity), CardDTO.class);
    }

  /*  @Transactional
    public AddressVO disableUser(Long id) {
        //cardDAO.disableCard(id);
        var entity = cardDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        cardDAO.disablePerson(id);
        return DozerConverter.parseObject(entity, AddressVO.class);
    }*/

    public void delete(Long id) {
        Card entity = cardDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        cardDAO.delete(entity);
    }
}
