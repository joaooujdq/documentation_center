package com.example.documentation_center.dtos;

import com.example.documentation_center.dtos.ValidationsGroups.BranchId;
import com.example.documentation_center.models.Branch;
import com.example.documentation_center.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BranchDTO extends RepresentationModel<BranchDTO>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Mapping("id")
    @JsonProperty("codigo_branch")
    private Long key;
    private Long idUser;
    @JsonProperty("nome_branch")
    private String nome;
    @JsonProperty("descricao_branch")
    private String descricao;
    @JsonProperty("data_branch")
    private LocalDate dataHora;

    //@ConvertGroup(from = Default.class, to = ValidationsGroups.UserId.class)
    //@Valid
    //private UserDTO userDTO;

    public BranchDTO(){

    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
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
        if (!(o instanceof BranchDTO branchDTO)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getKey(), branchDTO.getKey()) && Objects.equals(getIdUser(), branchDTO.getIdUser()) && Objects.equals(getNome(), branchDTO.getNome()) && Objects.equals(getDescricao(), branchDTO.getDescricao()) && Objects.equals(getDataHora(), branchDTO.getDataHora());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getKey(), getIdUser(), getNome(), getDescricao(), getDataHora());
    }
}
