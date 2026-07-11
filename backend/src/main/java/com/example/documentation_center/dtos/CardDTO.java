package com.example.documentation_center.dtos;

import com.example.documentation_center.dtos.ValidationsGroups.CardId;
import com.example.documentation_center.models.Card;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import com.github.dozermapper.core.Mapping;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class CardDTO extends RepresentationModel<CardDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Mapping("id")
    @JsonProperty("id")
    private Long key;
    private Long idFolder;
    private Long idBranch;
    private Long idUser;
    private String nome;
    private String descricao;
    private String imageLink;
    private String thumbnail;
    private LocalDate dataHora;

    @Size(max = 500)
    @JsonProperty("resumo_card")
    private String resumo;

    @Size(max = 300)
    @JsonProperty("tags_card")
    private String tags;

    @Size(max = 100)
    @JsonProperty("categoria_card")
    private String categoria;

    //@ConvertGroup(from = Default.class, to = ValidationsGroups.FolderId.class)
    //@Valid
    //private FolderDTO folderDTO;

    //@ConvertGroup(from = Default.class, to = ValidationsGroups.BranchId.class)
    //@Valid
    //private BranchDTO branchDTO;

    //@ConvertGroup(from = Default.class, to = ValidationsGroups.UserId.class)
    //@Valid
    //private UserDTO userDTO;

    public CardDTO() {
    }

    public CardDTO(Card obj) {
        key = obj.getId();
        nome = obj.getNome();
        descricao = obj.getDescricao();
        imageLink = obj.getImageLink();
        thumbnail = obj.getThumbnail();
        dataHora = obj.getDataHora();
        resumo = obj.getResumo();
        tags = obj.getTags();
        categoria = obj.getCategoria();
        idFolder = obj.getIdFolder();
        idBranch = obj.getIdBranch();
        idUser = obj.getIdUser();
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public LocalDate getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDate dataHora) {
        this.dataHora = dataHora;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(Long idBranch) {
        this.idBranch = idBranch;
    }

    public Long getIdFolder() {
        return idFolder;
    }

    public void setIdFolder(Long idFolder) {
        this.idFolder = idFolder;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CardDTO cardDTO)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getKey(), cardDTO.getKey()) && Objects.equals(getIdFolder(), cardDTO.getIdFolder()) && Objects.equals(getIdBranch(), cardDTO.getIdBranch()) && Objects.equals(getIdUser(), cardDTO.getIdUser()) && Objects.equals(getNome(), cardDTO.getNome()) && Objects.equals(getDescricao(), cardDTO.getDescricao()) && Objects.equals(getImageLink(), cardDTO.getImageLink()) && Objects.equals(getThumbnail(), cardDTO.getThumbnail()) && Objects.equals(getDataHora(), cardDTO.getDataHora());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getKey(), getIdFolder(), getIdBranch(), getIdUser(), getNome(), getDescricao(), getImageLink(), getThumbnail(), getDataHora());
    }

    public String getResumo() { return resumo; }
    public void setResumo(String resumo) { this.resumo = resumo; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
