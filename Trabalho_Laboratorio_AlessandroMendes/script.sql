-- 1. Criação do Banco de Dados
CREATE DATABASE IF NOT EXISTS controle_laboratorio;
USE controle_laboratorio;

-- 2. Criação da Tabela de Alunos
CREATE TABLE IF NOT EXISTS aluno (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     nome VARCHAR(100) NOT NULL,
    matricula VARCHAR(20) NOT NULL UNIQUE,
    curso VARCHAR(50) NOT NULL
    );

-- 3. Criação da Tabela de Equipamentos
CREATE TABLE IF NOT EXISTS equipamento (
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(50),
    disponivel BOOLEAN DEFAULT TRUE
    );

-- 4. Criação da Tabela de Empréstimos
CREATE TABLE IF NOT EXISTS emprestimo (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          id_aluno INT NOT NULL,
                                          id_equipamento INT NOT NULL,
                                          data_emprestimo VARCHAR(10) NOT NULL,
    data_devolucao VARCHAR(10),
    status VARCHAR(20) DEFAULT 'ATIVO',
    FOREIGN KEY (id_aluno) REFERENCES aluno(id),
    FOREIGN KEY (id_equipamento) REFERENCES equipamento(id)
    );

-- 5. Dados Iniciais (Resetando tudo para o teste ficar limpo)
TRUNCATE TABLE emprestimo;
DELETE FROM aluno;
DELETE FROM equipamento;

-- Inserindo alunos
INSERT INTO aluno (id, nome, matricula, curso) VALUES (1, 'Jose Silva', '2024001', 'Computacao');
INSERT INTO aluno (id, nome, matricula, curso) VALUES (2, 'Alessandro Mendes', '2024002', 'Computacao');

-- Inserindo equipamentos
INSERT INTO equipamento (id, nome, tipo, disponivel) VALUES (1, 'Projetor Epson', 'Multimidia', TRUE);
INSERT INTO equipamento (id, nome, tipo, disponivel) VALUES (2, 'Notebook Dell', 'Informatica', TRUE);