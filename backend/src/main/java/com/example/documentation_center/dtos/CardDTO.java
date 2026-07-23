package com.example.documentation_center.dtos;

import com.example.documentation_center.models.Card;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class CardDTO extends RepresentationModel<CardDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Mapping("id")
    @JsonProperty("codigo_card")
    private Long key;
    private Long idFolder;
    private Long idBranch;
    private Long idUser;
    @JsonProperty("nome_card")
    private String nome;
    @JsonProperty("descricao_card")
    private String descricao;
    @JsonProperty("thumbnail_card")
    private String thumbnail;
    @JsonProperty("data_card")
    private LocalDate dataHora;
    @JsonProperty(value = "folderDTO", access = JsonProperty.Access.WRITE_ONLY)
    private FolderDTO folderDTO;

    @Size(max = 500)
    @JsonProperty("resumo_card")
    private String resumo;

    @Size(max = 300)
    @JsonProperty("tags_card")
    private String tags;

    @Size(max = 100)
    @JsonProperty("categoria_card")
    private String categoria;

    public CardDTO() {}

    public CardDTO(Card obj) {
        key = obj.getId();
        nome = obj.getNome();
        descricao = obj.getDescricao();
        thumbnail = obj.getThumbnail();
        dataHora = obj.getDataHora();
        resumo = obj.getResumo();
        tags = obj.getTags();
        categoria = obj.getCategoria();
        idFolder = obj.getIdFolder();
        idBranch = obj.getIdBranch();
        idUser = obj.getIdUser();
    }

    public Long getKey() { return key; }
    public void setKey(Long key) { this.key = key; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    public LocalDate getDataHora() { return dataHora; }
    public void setDataHora(LocalDate dataHora) { this.dataHora = dataHora; }

    public Long getIdFolder() { return idFolder; }
    public void setIdFolder(Long idFolder) { this.idFolder = idFolder; }

    public Long getIdBranch() { return idBranch; }
    public void setIdBranch(Long idBranch) { this.idBranch = idBranch; }

    public Long getIdUser() { return idUser; }
    public void setIdUser(Long idUser) { this.idUser = idUser; }

    public String getResumo() { return resumo; }
    public void setResumo(String resumo) { this.resumo = resumo; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public FolderDTO getFolderDTO() { return folderDTO; }
    public void setFolderDTO(FolderDTO folderDTO) { this.folderDTO = folderDTO; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CardDTO cardDTO)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getKey(), cardDTO.getKey()) && Objects.equals(getIdFolder(), cardDTO.getIdFolder())
                && Objects.equals(getIdBranch(), cardDTO.getIdBranch()) && Objects.equals(getIdUser(), cardDTO.getIdUser())
                && Objects.equals(getNome(), cardDTO.getNome()) && Objects.equals(getDescricao(), cardDTO.getDescricao())
                && Objects.equals(getThumbnail(), cardDTO.getThumbnail()) && Objects.equals(getDataHora(), cardDTO.getDataHora());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getKey(), getIdFolder(), getIdBranch(), getIdUser(),
                getNome(), getDescricao(), getThumbnail(), getDataHora());
    }
}
