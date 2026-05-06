package br.edu.ifpa.laboratorio.dao;

import br.edu.ifpa.laboratorio.database.Conexao;
import br.edu.ifpa.laboratorio.model.Aluno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AlunoDAO {
    public void salvar(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, matricula, curso) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getMatricula());
            stmt.setString(3, aluno.getCurso());

            stmt.executeUpdate();
            System.out.println("Aluno salvo com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar aluno: " + e.getMessage());
        }
    }
}