package com.example.documentation_center.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Assinaturas")
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @ManyToOne(optional = false)
    private User userObj;

    @ManyToOne
    private Branch branchObj;

    @ManyToOne
    private Folder folderObj;

    @Column(name = "data", nullable = false)
    private LocalDate dataHora;

    public Assinatura() {
    }

    public Assinatura(User userObj, Branch branchObj, Folder folderObj) {
        this.userObj = userObj;
        this.branchObj = branchObj;
        this.folderObj = folderObj;
        this.dataHora = LocalDate.now();
    }

    public Integer getCodigo() { return codigo; }
    public void setCodigo(Integer codigo) { this.codigo = codigo; }

    public User getUserObj() { return userObj; }
    public void setUserObj(User userObj) { this.userObj = userObj; }

    public Branch getBranchObj() { return branchObj; }
    public void setBranchObj(Branch branchObj) { this.branchObj = branchObj; }

    public Folder getFolderObj() { return folderObj; }
    public void setFolderObj(Folder folderObj) { this.folderObj = folderObj; }

    public LocalDate getDataHora() { return dataHora; }
    public void setDataHora(LocalDate dataHora) { this.dataHora = dataHora; }
}
