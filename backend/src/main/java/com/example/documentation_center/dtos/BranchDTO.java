package com.example.documentation_center.dtos;

import com.example.documentation_center.dtos.ValidationsGroups.BranchId;
import com.example.documentation_center.models.Branch;
import com.example.documentation_center.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"codigo_branch", "nome_branch", "descricao_branch", "data_branch"})
public class BranchDTO extends RepresentationModel<BranchDTO>  {

    @JsonProperty("codigo_branch")
    private Integer codigo;

    @NotBlank
    @Size(max = 60)
    @JsonProperty("nome_branch")
    private String nome;

    @Size(max = 255)
    @JsonProperty("descricao_branch")
    private String descricao;

    @JsonProperty("data_branch")
    private LocalDate dataHora;

    @ConvertGroup(from = Default.class, to = ValidationsGroups.UserId.class)
    @Valid
    private UserDTO userDTO;

    public BranchDTO(){

    }

    public BranchDTO(Branch obj) {
        codigo = obj.getCodigo();
        nome = obj.getNome();
        descricao = obj.getDescricao();
        dataHora = obj.getDataHora();
        userDTO = new UserDTO(obj.getUserObj());
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public LocalDate getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDate dataHora) {
        this.dataHora = dataHora;
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

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

}
