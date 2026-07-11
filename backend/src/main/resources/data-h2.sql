-- ============================================================
-- Dados de teste — banco H2 in-memory (perfil h2)
-- FK circulares resolvidas: inserção com NULL → UPDATE
-- ============================================================

SET REFERENTIAL_INTEGRITY FALSE;

-- ----------------------------
-- Usuários
-- ----------------------------
INSERT INTO Users (codigo, nome, descricao, senha, admin, data, branch_obj_codigo) VALUES
(1, 'Admin TJGO',      'Administrador da plataforma DTI',        'admin123',  'true',  '2026-01-10', NULL),
(2, 'Lucas Lacerda',   'Desenvolvedor Backend — Especialização', 'senha123',  'false', '2026-01-11', NULL),
(3, 'João Marcelo',    'Desenvolvedor Frontend — Especialização','senha123',  'false', '2026-01-12', NULL),
(4, 'Ana Beatriz',     'Analista de Sistemas DTI',               'senha123',  'false', '2026-01-13', NULL);

-- ----------------------------
-- Times (Branchs)
-- ----------------------------
INSERT INTO Branchs (codigo, nome, descricao, data, user_obj_codigo, folder_obj_codigo) VALUES
(1, 'Time Backend',       'Equipe de desenvolvimento backend do TJGO',    '2026-01-10', 1, NULL),
(2, 'Time Frontend',      'Equipe de desenvolvimento frontend do TJGO',   '2026-01-10', 1, NULL),
(3, 'Time Infraestrutura','Equipe de infraestrutura e DevOps do TJGO',    '2026-01-10', 1, NULL);

-- ----------------------------
-- Sistemas (Folders)
-- ----------------------------
INSERT INTO Folders (codigo, nome, descricao, data, branch_obj_codigo, card_obj_codigo) VALUES
(1, 'PROJUD',       'Sistema de Processo Judicial Digital',         '2026-01-10', 1, NULL),
(2, 'SEI',          'Sistema Eletrônico de Informações',            '2026-01-10', 1, NULL),
(3, 'Portal TJGO',  'Portal público de serviços do TJGO',          '2026-01-10', 2, NULL),
(4, 'Intranet',     'Intranet institucional dos servidores',        '2026-01-10', 2, NULL),
(5, 'Infraestrutura','Servidores, redes e pipelines de CI/CD',     '2026-01-10', 3, NULL);

-- ----------------------------
-- Documentações (Cards)
-- ----------------------------
INSERT INTO Cards (codigo, nome, descricao, image_link, thumbnail, data, resumo, tags, categoria, folder_obj_codigo) VALUES
(1,
 'Deploy do PROJUD com Docker',
 'Passo a passo completo para realizar o deploy do sistema PROJUD em ambiente de produção utilizando Docker Compose. Inclui configuração de variáveis de ambiente, healthchecks e rollback.',
 NULL, NULL, '2026-02-01',
 'O PROJUD é deployado via Docker Compose em servidores RHEL do TJGO. O processo envolve build da imagem, push para o registry interno e restart controlado dos containers. Monitoramento pós-deploy é feito via Grafana.',
 'docker,deploy,produção,docker-compose,devops',
 'DevOps',
 1),

(2,
 'Configuração do Oracle Database no PROJUD',
 'Como configurar a conexão JNDI com o Oracle Database 19c no servidor JBoss para o PROJUD. Inclui configuração do datasource, pool de conexões e troubleshooting.',
 NULL, NULL, '2026-02-05',
 'A conexão Oracle é configurada via JNDI no JBoss EAP. As credenciais são armazenadas no vault institucional e nunca devem ser commitadas no repositório. O pool de conexões padrão é 10 mín / 50 máx.',
 'oracle,jndi,jboss,banco-de-dados,pool',
 'Banco de Dados',
 1),

(3,
 'Autenticação SSO no SEI',
 'Guia de integração do SEI com o sistema de Single Sign-On institucional do TJGO via SAML 2.0 e Keycloak.',
 NULL, NULL, '2026-02-10',
 'O SEI usa SAML 2.0 para SSO via servidor Keycloak do TJGO. A integração exige certificado digital válido emitido pela CA interna. A renovação é anual e deve ser solicitada ao setor de TI com 30 dias de antecedência.',
 'sso,saml,keycloak,autenticação,segurança',
 'Segurança',
 2),

(4,
 'API REST do Portal TJGO',
 'Documentação técnica dos endpoints REST expostos pelo Portal TJGO. Padrão OpenAPI 3.0, autenticação JWT, versionamento por header.',
 NULL, NULL, '2026-03-01',
 'O Portal expõe APIs REST no padrão OpenAPI 3.0 com autenticação via JWT. O rate limiting é de 100 req/min por IP. A documentação interativa está disponível em /swagger-ui na rota interna.',
 'api,rest,jwt,openapi,swagger',
 'API',
 3),

(5,
 'Build e Publicação do Frontend React',
 'Como buildar e publicar o frontend React do Portal TJGO. Inclui configuração de variáveis de ambiente, pipeline Jenkins e publicação no Nginx.',
 NULL, NULL, '2026-03-05',
 'O build usa npm run build e é publicado em um container Nginx. Variáveis de ambiente devem ser configuradas antes do build pois são embutidas no bundle. O CI/CD é gerenciado pelo Jenkins interno do TJGO.',
 'react,build,nginx,jenkins,ci-cd',
 'Frontend',
 3),

(6,
 'Configuração de VPN e Acesso Remoto',
 'Como solicitar e configurar o acesso VPN para conexão remota à intranet do TJGO. Inclui instalação do cliente OpenVPN e resolução de problemas comuns.',
 NULL, NULL, '2026-04-01',
 'A VPN institucional usa OpenVPN com certificado emitido pela CA interna do TJGO. A solicitação é feita via chamado no SEI no serviço "Infraestrutura > VPN". O acesso é liberado em até 2 dias úteis.',
 'vpn,acesso-remoto,openvpn,segurança,infraestrutura',
 'Infraestrutura',
 5),

(7,
 'Pipeline CI/CD com Jenkins',
 'Documentação do pipeline de integração e entrega contínua utilizado pelos times do TJGO. Inclui stages de build, teste, análise estática e deploy.',
 NULL, NULL, '2026-04-10',
 'O pipeline Jenkins executa build Maven, testes unitários, análise SonarQube e deploy automático para staging. O deploy em produção requer aprovação manual do líder técnico. Notificações são enviadas via e-mail institucional.',
 'jenkins,ci-cd,sonarqube,maven,pipeline',
 'DevOps',
 5);

-- ----------------------------
-- Vincular usuários ao time (UPDATE circular)
-- ----------------------------
UPDATE Users SET branch_obj_codigo = 1 WHERE codigo = 2;
UPDATE Users SET branch_obj_codigo = 2 WHERE codigo = 3;
UPDATE Users SET branch_obj_codigo = 3 WHERE codigo = 4;

-- ----------------------------
-- Assinaturas
-- ----------------------------
INSERT INTO Assinaturas (codigo, user_obj_codigo, branch_obj_codigo, folder_obj_codigo, data) VALUES
(1, 2, 1,    NULL, '2026-01-20'),  -- Lucas assina Time Backend
(2, 2, NULL, 2,    '2026-02-01'),  -- Lucas assina sistema SEI
(3, 3, 2,    NULL, '2026-01-20'),  -- João assina Time Frontend
(4, 3, NULL, 3,    '2026-02-01'),  -- João assina sistema Portal
(5, 4, 3,    NULL, '2026-01-25'),  -- Ana assina Time Infraestrutura
(6, 1, 1,    NULL, '2026-01-10');  -- Admin assina Time Backend

-- ----------------------------
-- Notificações
-- ----------------------------
INSERT INTO Notificacoes (codigo, user_obj_codigo, card_obj_codigo, mensagem, lida, data) VALUES
(1, 2, 3, 'Nova documentação no SEI: Autenticação SSO no SEI',               FALSE, '2026-02-10'),
(2, 3, 4, 'Nova documentação no Portal TJGO: API REST do Portal TJGO',        FALSE, '2026-03-01'),
(3, 3, 5, 'Nova documentação no Portal TJGO: Build e Publicação do Frontend', TRUE,  '2026-03-05'),
(4, 4, 6, 'Nova doc em Infraestrutura: Configuração de VPN e Acesso Remoto',  FALSE, '2026-04-01'),
(5, 4, 7, 'Nova doc em Infraestrutura: Pipeline CI/CD com Jenkins',            FALSE, '2026-04-10'),
(6, 1, 1, 'Nova documentação no Time Backend: Deploy do PROJUD com Docker',   TRUE,  '2026-02-01');

SET REFERENTIAL_INTEGRITY TRUE;
