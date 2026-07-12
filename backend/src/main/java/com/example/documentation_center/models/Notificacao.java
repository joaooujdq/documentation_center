package com.example.documentation_center.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Notificacoes")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @ManyToOne(optional = false)
    private User userObj;

    @ManyToOne(optional = false)
    private Card cardObj;

    @Column(name = "mensagem", nullable = false, length = 300)
    private String mensagem;

    @Column(name = "lida", nullable = false)
    private Boolean lida;

    @Column(name = "data", nullable = false)
    private LocalDate dataHora;

    public Notificacao() {
    }

    public Notificacao(User userObj, Card cardObj, String mensagem) {
        this.userObj = userObj;
        this.cardObj = cardObj;
        this.mensagem = mensagem;
        this.lida = false;
        this.dataHora = LocalDate.now();
    }

    public Integer getCodigo() { return codigo; }
    public void setCodigo(Integer codigo) { this.codigo = codigo; }

    public User getUserObj() { return userObj; }
    public void setUserObj(User userObj) { this.userObj = userObj; }

    public Card getCardObj() { return cardObj; }
    public void setCardObj(Card cardObj) { this.cardObj = cardObj; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public Boolean getLida() { return lida; }
    public void setLida(Boolean lida) { this.lida = lida; }

    public LocalDate getDataHora() { return dataHora; }
    public void setDataHora(LocalDate dataHora) { this.dataHora = dataHora; }
}
