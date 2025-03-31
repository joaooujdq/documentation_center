package com.example.documentation_center.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "admin")
    private String admin;

    @Column(name = "data", nullable = false)
    private LocalDate dataHora;

    @ManyToOne
    private Branch branchObj;

    public User(){

    }

    public User(Integer codigo, String admin, String nome, String descricao, String senha) {
        switch (admin) {
            case "option1":
                this.admin = "true";
                break;
            case "option2":
                this.admin = "false";
                break;
            default:
                this.admin = "false";
                break;
        }
        if(codigo == 1){
            this.admin="true";
        }
        this.nome = nome;
        this.descricao = descricao;
        LocalDate agora = LocalDate.now();
        this.dataHora = agora;
        this.senha = senha;
        this.codigo = codigo;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Branch getBranchObj() {
        return branchObj;
    }

    public void setBranchObj(Branch branchObj) {
        this.branchObj = branchObj;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

