package com.example.documentation_center.models;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Imagens")
public class Imagem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "dados", nullable = false)
    private byte[] dados;

    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;

    @Column(name = "nome", length = 255)
    private String nome;

    public Imagem() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public byte[] getDados() { return dados; }
    public void setDados(byte[] dados) { this.dados = dados; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
