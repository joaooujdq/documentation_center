package com.example.documentation_center.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Folders")
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data", nullable = false)
    private LocalDate dataHora;

    @ManyToOne
    private Branch branchObj;

    @ManyToOne
    private Card cardObj;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "folderObj", orphanRemoval = true)
    private List<Branch> branchs = new ArrayList<>();

    public Folder(){

    }

    public Folder(Integer codigo, String descricao, String nome, Branch branchObj) {
        this.codigo = codigo;
        this.branchObj = branchObj;
        LocalDate agora = LocalDate.now();
        this.dataHora = agora;
        this.descricao = descricao;
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Branch getBranchObj() {
        return branchObj;
    }

    public void setBranchObj(Branch branchObj) {
        this.branchObj = branchObj;
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

    public List<Branch> getBranchs() {
        return branchs;
    }

    public void setBranchs(List<Branch> branchs) {
        this.branchs = branchs;
    }

    public Card getCardObj() {
        return cardObj;
    }

    public void setCardObj(Card cardObj) {
        this.cardObj = cardObj;
    }
}

