package com.example.documentation_center.ExceptionHandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandardError {
    private Integer codigo;
    private LocalDateTime momento;
    private String descricao;
    private List<Fields> campos;

    public static class Fields {
        private String nome;
        private String mensagem;
        
        public Fields(final String nome, final String mensagem) {
            this.nome = nome;
            this.mensagem = mensagem;
        }
        public String getNome() {
            return nome;
        }
        public void setNome(String nome) {
            this.nome = nome;
        }
        public String getMensagem() {
            return mensagem;
        }
        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }
    }
    public StandardError(Integer codigo, LocalDateTime momento, String descricao, List<Fields> campos) {
        this.codigo = codigo;
        this.momento = momento;
        this.descricao = descricao;
        this.campos = campos;
    }
    public Integer getCodigo() {
        return codigo;
    }
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    public LocalDateTime getMomento() {
        return momento;
    }
    public void setMomento(LocalDateTime momento) {
        this.momento = momento;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public List<Fields> getCampos() {
        return campos;
    }
    public void setCampos(List<Fields> campos) {
        this.campos = campos;
    }
}
