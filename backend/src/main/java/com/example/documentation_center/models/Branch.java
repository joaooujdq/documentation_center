package com.example.documentation_center.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Branchs")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data")
    private LocalDate dataHora;

    @ManyToOne
    private User userObj;

    @ManyToOne
    private Folder folderObj;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchObj", orphanRemoval = true)
    private List<User> users = new ArrayList<>();

    public Branch(){

    }

    public Branch(Integer codigo, String descricao, String nome, User userObj) {
        this.codigo = codigo;
        this.userObj = userObj;
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

    public User getUserObj() {
        return userObj;
    }

    public void setUserObj(User userObj) {
        this.userObj = userObj;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Folder getFolderObj() {
        return folderObj;
    }

    public void setFolderObj(Folder folderObj) {
        this.folderObj = folderObj;
    }
}

