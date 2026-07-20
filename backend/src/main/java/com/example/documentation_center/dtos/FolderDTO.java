package com.example.documentation_center.dtos;

import com.example.documentation_center.dtos.ValidationsGroups.FolderId;
import com.example.documentation_center.models.Folder;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import com.github.dozermapper.core.Mapping;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class FolderDTO extends RepresentationModel<FolderDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Mapping("id")
    @JsonProperty("codigo_folder")
    //private Integer codigo;
    private Long key;
    private Long idBranch;
    private Long idUser;
    @JsonProperty("nome_folder")
    private String nome;
    @JsonProperty("descricao_folder")
    private String descricao;
    @JsonProperty("data_folder")
    private LocalDate dataHora;

    //@ConvertGroup(from = Default.class, to = ValidationsGroups.BranchId.class)
    //@Valid
    //private BranchDTO branchDTO; {


    //@ConvertGroup(from = Default.class, to = ValidationsGroups.UserId.class)
    //@Valid
    //private UserDTO userDTO;

    public FolderDTO() {

    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Long getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(Long idBranch) {
        this.idBranch = idBranch;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDate dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FolderDTO folderDTO)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getKey(), folderDTO.getKey()) && Objects.equals(getIdBranch(), folderDTO.getIdBranch()) && Objects.equals(getIdUser(), folderDTO.getIdUser()) && Objects.equals(getNome(), folderDTO.getNome()) && Objects.equals(getDescricao(), folderDTO.getDescricao()) && Objects.equals(getDataHora(), folderDTO.getDataHora());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getKey(), getIdBranch(), getIdUser(), getNome(), getDescricao(), getDataHora());
    }
}
