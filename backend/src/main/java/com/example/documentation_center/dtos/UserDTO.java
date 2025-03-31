package com.example.documentation_center.dtos;

import com.example.documentation_center.dtos.ValidationsGroups.UserId;
import com.example.documentation_center.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@JsonPropertyOrder({"codigo_user", "nome_user",  "descricao_user", "senha_user", "admin_user", "data_user"})
public class UserDTO extends RepresentationModel<UserDTO>  {

    @JsonProperty("codigo_user")
    private Integer codigo;

    @NotBlank
    @Size(max = 60)
    @JsonProperty("nome_user")
    private String nome;

    @JsonProperty("descricao_user")
    private String descricao;

    @NotBlank
    @Size(max = 60)
    @JsonProperty("senha_user")
    private String senha;

    @JsonProperty("admin_user")
    private String admin;

    @JsonProperty("data_user")
    private LocalDate dataHora;


    public UserDTO(){

    }

    public UserDTO(User obj) {
        codigo = obj.getCodigo();
        nome = obj.getNome();
        descricao = obj.getDescricao();
        senha = obj.getSenha();
        admin = obj.getAdmin();
        dataHora = obj.getDataHora();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public LocalDate getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDate dataHora) {
        this.dataHora = dataHora;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
}
