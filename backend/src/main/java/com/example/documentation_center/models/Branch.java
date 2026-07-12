package com.example.documentation_center.models;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

//import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Branchs")
public class Branch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data")
    private LocalDate dataHora;

    public Branch(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataHora = LocalDate.now();
    }

    //@ManyToOne
    //private User userObj;

    //@Column(name = "id_user", nullable = false, length = 10)
    //private Long idUser;

    //@ManyToOne
    //private Folder folderObj;

    //@Column(name = "id_folder", nullable = false, length = 10)
    //private Long idFolder;

    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "branchObj", orphanRemoval = true)
    //private List<User> users = new ArrayList<>();

    public Branch() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof Branch branch)) return false;
        return Objects.equals(getId(), branch.getId()) && Objects.equals(getNome(), branch.getNome()) && Objects.equals(getDescricao(), branch.getDescricao()) && Objects.equals(getDataHora(), branch.getDataHora());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getDescricao(), getDataHora());
    }
}

