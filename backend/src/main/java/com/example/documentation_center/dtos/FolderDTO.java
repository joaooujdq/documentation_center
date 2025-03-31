package com.example.documentation_center.dtos;

import com.example.documentation_center.dtos.ValidationsGroups.FolderId;
import com.example.documentation_center.models.Folder;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.time.LocalDate;

@JsonPropertyOrder({"codigo_folder", "nome_folder", "descricao_folder", "data_folder"})
public class FolderDTO extends RepresentationModel<FolderDTO> {

    @JsonProperty("codigo_folder")
    private Integer codigo;

    @NotBlank
    @Size(max = 60)
    @JsonProperty("nome_folder")
    private String nome;


    @Size(max = 255)
    @JsonProperty("descricao_folder")
    private String descricao;

    @JsonProperty("data_folder")
    private LocalDate dataHora;

    @ConvertGroup(from = Default.class, to = ValidationsGroups.BranchId.class)
    @Valid
    private BranchDTO branchDTO;

    @ConvertGroup(from = Default.class, to = ValidationsGroups.UserId.class)
    @Valid
    private UserDTO userDTO;

    public FolderDTO() {

    }

    public FolderDTO(Folder obj) {
        codigo = obj.getCodigo();
        descricao = obj.getDescricao();
        branchDTO = new BranchDTO(obj.getBranchObj());
        dataHora = obj.getDataHora();
        nome = obj.getNome();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public BranchDTO getBranchDTO() {
        return branchDTO;
    }

    public void setBranchDTO(BranchDTO branchDTO) {
        this.branchDTO = branchDTO;
    }

    public LocalDate getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDate dataHora) {
        this.dataHora = dataHora;
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

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
