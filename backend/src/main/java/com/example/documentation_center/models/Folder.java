package com.example.documentation_center.models;

import jakarta.persistence.*;

//import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Folders")
public class Folder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_branch", nullable = false, length = 10)
    private Long idBranch;

    @Column(name = "id_user", nullable = false, length = 10)
    private Long idUser;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data", nullable = false)
    private LocalDate dataHora;

    public Folder(Long id, Long idBranch, Long idUser, String nome, String descricao) {
        this.id = id;
        this.idBranch = idBranch;
        this.idUser = idUser;
        this.nome = nome;
        this.descricao = descricao;
        this.dataHora = LocalDate.now();
    }


    //@ManyToOne
    //private Branch branchObj;


    //@ManyToOne
    //private Card cardObj;

    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "folderObj", orphanRemoval = true)
    //private List<Branch> branchs = new ArrayList<>();

    public Folder(){

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof Folder folder)) return false;
        return Objects.equals(getId(), folder.getId()) && Objects.equals(getIdBranch(), folder.getIdBranch()) && Objects.equals(getIdUser(), folder.getIdUser()) && Objects.equals(getNome(), folder.getNome()) && Objects.equals(getDescricao(), folder.getDescricao()) && Objects.equals(getDataHora(), folder.getDataHora());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getIdBranch(), getIdUser(), getNome(), getDescricao(), getDataHora());
    }
}

