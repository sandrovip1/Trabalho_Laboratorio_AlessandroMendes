package br.edu.ifpa.laboratorio.dao;

import br.edu.ifpa.laboratorio.database.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EquipamentoDAO {

    public void listarDisponiveis() {
        String sql = "SELECT * FROM equipamento WHERE disponivel = TRUE";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("--- EQUIPAMENTOS DISPONÍVEIS ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " | Nome: " + rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar equipamentos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método salvar que aparece no seu diagrama de classes
    public void salvar(br.edu.ifpa.laboratorio.model.Equipamento eq) {
        String sql = "INSERT INTO equipamento (nome, tipo, disponivel) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, eq.getNome());
            // stmt.setString(2, eq.getTipo()); // Adicione se sua model tiver tipo
            stmt.setBoolean(3, eq.isDisponivel());
            stmt.executeUpdate();

            System.out.println("Equipamento salvo com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}