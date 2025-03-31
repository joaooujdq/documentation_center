package com.example.documentation_center.dtos;

import com.example.documentation_center.dtos.ValidationsGroups.CardId;
import com.example.documentation_center.models.Card;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.time.LocalDate;

@JsonPropertyOrder({"codigo_card", "nome_card", "descricao_card", "imageLink_card", "thumbnail_card", "data_card"})
public class CardDTO extends RepresentationModel<CardDTO> {

    @JsonProperty("codigo_card")
    private Integer codigo;

    @NotBlank
    @Size(max = 60)
    @JsonProperty("nome_card")
    private String nome;

    @Size(max = 255)
    @JsonProperty("descricao_card")
    private String descricao;

    @Size(max = 255)
    @JsonProperty("imageLink_card")
    private String imageLink;

    @Size(max = 255)
    @JsonProperty("thumbnail_card")
    private String thumbnail;

    @JsonProperty("data_card")
    private LocalDate dataHora;

    @ConvertGroup(from = Default.class, to = ValidationsGroups.FolderId.class)
    @Valid
    private FolderDTO folderDTO;

    @ConvertGroup(from = Default.class, to = ValidationsGroups.BranchId.class)
    @Valid
    private BranchDTO branchDTO;

    @ConvertGroup(from = Default.class, to = ValidationsGroups.UserId.class)
    @Valid
    private UserDTO userDTO;

    public CardDTO() {
    }

    public CardDTO(Card obj) {
        codigo = obj.getCodigo();
        nome = obj.getNome();
        descricao = obj.getDescricao();
        imageLink = obj.getImageLink();
        thumbnail = obj.getThumbnail();
        dataHora = obj.getDataHora();
        folderDTO = new FolderDTO(obj.getFolderObj());
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public FolderDTO getFolderDTO() {
        return folderDTO;
    }

    public void setFolderDTO(FolderDTO folderDTO) {
        this.folderDTO = folderDTO;
    }

    public LocalDate getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDate dataHora) {
        this.dataHora = dataHora;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public BranchDTO getBranchDTO() {
        return branchDTO;
    }

    public void setBranchDTO(BranchDTO branchDTO) {
        this.branchDTO = branchDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}