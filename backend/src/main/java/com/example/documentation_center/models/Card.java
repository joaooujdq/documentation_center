package com.example.documentation_center.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "imageLink")
    private String imageLink;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "data", nullable = false)
    private LocalDate dataHora;

    @ManyToOne
    private Folder folderObj;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cardObj", orphanRemoval = true)
    private List<Folder> folders = new ArrayList<>();

    public Card(){

    }

    public Card(Integer codigo, String thumbnail, String imageLink, String descricao, String nome, Folder folderObj) {
        this.codigo = codigo;
        this.folderObj = folderObj;
        LocalDate agora = LocalDate.now();
        this.dataHora = agora;
        this.thumbnail = thumbnail;
        this.imageLink = imageLink;
        this.descricao = descricao;
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Folder getFolderObj() {
        return folderObj;
    }

    public void setFolderObj(Folder folderObj) {
        this.folderObj = folderObj;
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

    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }
}

