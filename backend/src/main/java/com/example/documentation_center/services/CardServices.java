package com.example.documentation_center.services;

import com.example.documentation_center.converter.DozerConverter;
import com.example.documentation_center.dtos.CardDTO;
import com.example.documentation_center.dtos.ScoreDTO;
import com.example.documentation_center.dtos.UserDTO;
import com.example.documentation_center.exception.ResourceNotFoundException;
import com.example.documentation_center.models.*;
import com.example.documentation_center.repositories.*;
import com.example.documentation_center.services.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CardServices {

    @Autowired
    CardDAO cardDAO;

    @Autowired
    ScoreDAO scoreDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    private ScoreServices service;

    public CardDTO create(CardDTO cardDTO) {
        var entity = DozerConverter.parseObject(cardDTO, Card.class);
        /*
        //verifica se o user ja existe dentro da tabela score
        if(scoreDAO.findScoreByNome(cardDTO.getNome()) == null){
            UserDTO userDTO = userDAO.findById(cardDTO.getIdUser());
            if(userDTO != null){
                //se o user ja esta na tabela de Score, consulta pontuação anterior e acrescenta
                Integer pontosAntes = service.findByScoreByNome(userDTO.getNome());
                ScoreDTO scoreDTO = service.update(new Score(userDTO.getNome(),userDTO.getDescricao(),pontosAntes + 1, LocalDate.now()));
            }

        }else{
            UserDTO userDTO = userDAO.findById(cardDTO.getIdUser());
            ScoreDTO scoreDTO = new ScoreDTO(userDTO.getNome(),userDTO.getDescricao(), 1, LocalDate.now());
            service.create(scoreDTO);
        }
        */
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
        //entity.setImageLink(card.getImageLink());
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

    /*

     CODIGO LUCAS EXCLUIDO NA RESOLUÇÃO DE CONFLITOS DO MERGE

    import java.util.Arrays;
    import java.util.List;

    @Service
    public class CardServices {
        private CardDAO cardDAO;
        private BranchDAO branchDAO;
        private FolderDAO folderDAO;
        private UserDAO userDAO;
        private IaService iaService;
        private NotificacaoServices notificacaoServices;

        @Transactional
        public CardDTO save(CardDTO obj) {
            if(obj.getNome()=="" ){
                throw new BusinessException("Os campos com * são obrigatórios!");
            }
            boolean isNovo = (obj.getCodigo() == null);
            Optional<Folder> folder = folderDAO.findById(obj.getFolderDTO().getCodigo());
            Card entity = new Card(obj.getCodigo() ,obj.getThumbnail(), obj.getImageLink(), obj.getDescricao(), obj.getNome(),
                    new Folder(folder.get().getCodigo(), folder.get().getDescricao(), folder.get().getNome(),
                            new Branch(folder.get().getBranchObj().getCodigo(), folder.get().getBranchObj().getDescricao(), folder.get().getBranchObj().getNome(),
                                    new User(folder.get().getBranchObj().getUserObj().getCodigo(), folder.get().getBranchObj().getUserObj().getAdmin(), folder.get().getBranchObj().getUserObj().getNome(), folder.get().getBranchObj().getUserObj().getDescricao(), folder.get().getBranchObj().getUserObj().getSenha()))));
            entity.setResumo(obj.getResumo());
            entity.setTags(obj.getTags());
            entity.setCategoria(obj.getCategoria());
            Card saved = cardDAO.save(entity);
            if (isNovo) {
                notificacaoServices.notificarAssinantes(saved);
            }
            return new CardDTO(saved);
        }




        @Transactional(readOnly = true)
        public Page<CardDTO> pesquisar(String nome, String categoria, Pageable pageable) {
            boolean temNome = nome != null && !nome.isBlank();
            boolean temCategoria = categoria != null && !categoria.isBlank();
            Page<Card> result;
            if (temNome && temCategoria) {
                result = cardDAO.findByNomeContainsIgnoreCaseAndCategoriaIgnoreCase(nome, categoria, pageable);
            } else if (temNome) {
                result = cardDAO.findByNomeContainsIgnoreCase(nome, pageable);
            } else if (temCategoria) {
                result = cardDAO.findByCategoriaIgnoreCase(categoria, pageable);
            } else {
                result = cardDAO.findAll(pageable);
            }
            return result.map(CardDTO::new);
        }

    /*public Page<CardDTO> findByRazaoContains(String razao, Pageable pageable) {
        Page<Card> result = cardDAO.findByRazaoContains(razao, pageable);
        return result.map(obj -> new CardDTO(obj));
    }

    public Page<CardDTO> findByEnderecoContains(String endereco, Pageable pageable) {
        Page<Card> result = cardDAO.findByEnderecoContains(endereco, pageable);
        return result.map(obj -> new CardDTO(obj));
    }

     */
       /*
        //<editor-fold defaultstate="collapsed" desc="delombok">
        @SuppressWarnings("all")
        public CardServices(CardDAO cardDAO, FolderDAO folderDAO, BranchDAO branchDAO, UserDAO userDAO,
                            IaService iaService, NotificacaoServices notificacaoServices) {
            this.cardDAO = cardDAO;
            this.folderDAO = folderDAO;
            this.branchDAO = branchDAO;
            this.userDAO = userDAO;
            this.iaService = iaService;
            this.notificacaoServices = notificacaoServices;
        }
    */
}
