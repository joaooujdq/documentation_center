package com.example.documentation_center.dtos;

import com.example.documentation_center.models.Assinatura;
import java.time.LocalDate;

public class AssinaturaDTO {

    private Integer codigo;
    private BranchInfo branchObj;
    private FolderInfo folderObj;
    private LocalDate dataHora;

    public AssinaturaDTO() {}

    public AssinaturaDTO(Assinatura a) {
        this.codigo = a.getCodigo();
        this.dataHora = a.getDataHora();
        if (a.getBranchObj() != null) {
            this.branchObj = new BranchInfo(a.getBranchObj().getCodigo(), a.getBranchObj().getNome());
        }
        if (a.getFolderObj() != null) {
            this.folderObj = new FolderInfo(a.getFolderObj().getCodigo(), a.getFolderObj().getNome());
        }
    }

    public static class BranchInfo {
        private Integer codigo;
        private String nome;
        public BranchInfo() {}
        public BranchInfo(Integer codigo, String nome) { this.codigo = codigo; this.nome = nome; }
        public Integer getCodigo() { return codigo; }
        public String getNome() { return nome; }
    }

    public static class FolderInfo {
        private Integer codigo;
        private String nome;
        public FolderInfo() {}
        public FolderInfo(Integer codigo, String nome) { this.codigo = codigo; this.nome = nome; }
        public Integer getCodigo() { return codigo; }
        public String getNome() { return nome; }
    }

    public Integer getCodigo() { return codigo; }
    public BranchInfo getBranchObj() { return branchObj; }
    public FolderInfo getFolderObj() { return folderObj; }
    public LocalDate getDataHora() { return dataHora; }
}
