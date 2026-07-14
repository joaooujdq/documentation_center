-- ============================================================
-- Migração: novos campos e tabelas (resumo, tags, assinaturas, notificações)
-- Compatível com Oracle (principal) e MySQL (dev)
-- ============================================================

-- 1. Novos campos na tabela Cards (documentações)
-- Oracle:
ALTER TABLE Cards ADD (resumo VARCHAR2(500), tags VARCHAR2(300), categoria VARCHAR2(100));
-- MySQL:
-- ALTER TABLE Cards ADD COLUMN resumo VARCHAR(500), ADD COLUMN tags VARCHAR(300), ADD COLUMN categoria VARCHAR(100);


-- 2. Tabela de Assinaturas
-- Oracle:
CREATE TABLE Assinaturas (
    codigo       NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_obj_codigo   NUMBER NOT NULL,
    branch_obj_codigo NUMBER,
    folder_obj_codigo NUMBER,
    data         DATE NOT NULL,
    CONSTRAINT fk_assinatura_user   FOREIGN KEY (user_obj_codigo)   REFERENCES Users(codigo),
    CONSTRAINT fk_assinatura_branch FOREIGN KEY (branch_obj_codigo) REFERENCES Branchs(codigo),
    CONSTRAINT fk_assinatura_folder FOREIGN KEY (folder_obj_codigo) REFERENCES Folders(codigo)
);
-- MySQL:
-- CREATE TABLE Assinaturas (
--     codigo            INT AUTO_INCREMENT PRIMARY KEY,
--     user_obj_codigo   INT NOT NULL,
--     branch_obj_codigo INT,
--     folder_obj_codigo INT,
--     data              DATE NOT NULL,
--     FOREIGN KEY (user_obj_codigo)   REFERENCES Users(codigo),
--     FOREIGN KEY (branch_obj_codigo) REFERENCES Branchs(codigo),
--     FOREIGN KEY (folder_obj_codigo) REFERENCES Folders(codigo)
-- );


-- 3. Tabela de Notificações
-- Oracle:
CREATE TABLE Notificacoes (
    codigo           NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_obj_codigo  NUMBER NOT NULL,
    card_obj_codigo  NUMBER NOT NULL,
    mensagem         VARCHAR2(300) NOT NULL,
    lida             NUMBER(1) DEFAULT 0 NOT NULL,
    data             DATE NOT NULL,
    CONSTRAINT fk_notif_user FOREIGN KEY (user_obj_codigo) REFERENCES Users(codigo),
    CONSTRAINT fk_notif_card FOREIGN KEY (card_obj_codigo) REFERENCES Cards(codigo)
);
-- MySQL:
-- CREATE TABLE Notificacoes (
--     codigo          INT AUTO_INCREMENT PRIMARY KEY,
--     user_obj_codigo INT NOT NULL,
--     card_obj_codigo INT NOT NULL,
--     mensagem        VARCHAR(300) NOT NULL,
--     lida            TINYINT(1) DEFAULT 0 NOT NULL,
--     data            DATE NOT NULL,
--     FOREIGN KEY (user_obj_codigo) REFERENCES Users(codigo),
--     FOREIGN KEY (card_obj_codigo) REFERENCES Cards(codigo)
-- );
