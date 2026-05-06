# Sistema de Controle de Empréstimo de Equipamentos

## Integrante
ALESSANDRO DE SOUZA MENDES

---

## Descrição do problema
O sistema visa gerenciar o empréstimo de equipamentos de um laboratório para alunos do IFPA Campus Tucuruí, garantindo o controle rigoroso de disponibilidade, histórico de uso e integridade dos dados através de uma arquitetura orientada a objetos (Java + MySQL).

---

## Requisitos Implementados (Conforme Roteiro)
Cadastro: Alunos e Equipamentos persistidos no banco de dados MySQL.
Controle de Disponibilidade: Alteração automática do status do equipamento (`TRUE`/`FALSE`) após cada operação de empréstimo.
Regras de Negócio:
Impedimento de empréstimo para equipamentos indisponíveis (bloqueio via lógica Java).
Validação de integridade para usuários ou itens inexistentes (via restrições de Foreign Key).
Persistência: Uso do padrão DAO (Data Access Object) e JDBC para comunicação robusta com o banco.

---

## Diagramas UML (Modelagem Astah)
O projeto seguiu rigorosamente a modelagem contida na pasta `/Imagens_UML`:
1. Diagrama de Casos de Uso: Define as interações do sistema.
2. Diagrama de Classes: Estrutura das entidades (Model), persistência (DAO) e Conexão.
3. Diagrama de Sequência: Fluxo detalhado da realização de um empréstimo.

---

## Modelo do Banco de Dados
O banco `controle_laboratorio` é composto por:
1. aluno: Dados cadastrais (ID, Nome, Matrícula, Curso).
2. equipamento: Descrição e status de disponibilidade (Boolean).
3. emprestimo: Vínculo entre as tabelas anteriores com controle de datas e status.

---

## Como Criar o Banco MySQL
1. Abra o MySQL Client ou Workbench.
2. Execute o conteúdo do arquivo `script.sql`.
3. Dica: O script realiza o reset das tabelas e insere os dados iniciais (Alunos 1 e 2 e Equipamentos 1 e 2).

---

## Como Executar o Projeto Java
1. No IntelliJ, confirme se o Maven carregou o `mysql-connector-j`.
2. Execute a classe `Main.java`.
3. O sistema processará automaticamente os testes de: empréstimo válido, bloqueio de duplicidade e validação de integridade.

---

## Testes Realizados (Tabela de Validação)

| Teste                    | Resultado Esperado                                         | Status |
|:-------------------------|:-----------------------------------------------------------|:-------|
| Cadastrar Aluno          | Inserção no banco com sucesso e ID automático.             | OK     |
| Cadastrar Equipamento    | Inserção com status inicial `disponivel = TRUE`.           | OK     |
| Realizar Empréstimo      | Vincular IDs e registrar no banco via JDBC.                | OK     |
| Bloqueio de Duplicidade  | Impedir empréstimo de item ocupado (Validação Java).       | OK     |
| Validação de Integridade | Bloqueio de empréstimo para IDs inexistentes (Erro de FK). | OK     |

---

## Decisões de Projeto
Padrão DAO: Separação total da lógica de SQL das classes de modelo.
Tratamento de IDs: Implementada checagem via `SELECT` antes do `INSERT` para evitar saltos de auto-incremento no MySQL em casos de falha.
Maven: Gerenciamento de dependências para garantir portabilidade do Driver JDBC.

---

## Validação Final (via SQL)
Para confirmar que o Java atualizou o banco corretamente, execute os comandos abaixo no MySQL após os testes:

-- Verifica se o empréstimo foi registrado
SELECT * FROM emprestimo;

-- Verifica se o equipamento ficou com status ocupado
SELECT nome, disponivel FROM equipamento;