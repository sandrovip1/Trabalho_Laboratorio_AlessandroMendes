package br.edu.ifpa.laboratorio.dao;

import br.edu.ifpa.laboratorio.database.Conexao;
import br.edu.ifpa.laboratorio.model.Emprestimo;
import java.sql.*;

public class EmprestimoDAO {

    public boolean realizarEmprestimo(Emprestimo emp) {
        // SQL para verificar se aluno e equipamento existem antes de tentar o insert
        String sqlValidar = "SELECT " +
                "(SELECT COUNT(*) FROM aluno WHERE id = ?) as aluno_ok, " +
                "(SELECT COUNT(*) FROM equipamento WHERE id = ?) as equip_ok";

        String sqlCheckDispo = "SELECT disponivel FROM equipamento WHERE id = ?";
        String sqlEmprestimo = "INSERT INTO emprestimo (id_aluno, id_equipamento, data_emprestimo, status) VALUES (?, ?, ?, 'ATIVO')";
        String sqlUpdate = "UPDATE equipamento SET disponivel = FALSE WHERE id = ?";

        try (Connection conn = Conexao.getConexao()) {
            if (conn == null) return false;
            conn.setAutoCommit(false);

            // 1. Validação de IDs (Impede o pulo do AUTO_INCREMENT no MySQL)
            try (PreparedStatement stmtValida = conn.prepareStatement(sqlValidar)) {
                stmtValida.setInt(1, emp.getIdAluno());
                stmtValida.setInt(2, emp.getIdEquipamento());
                try (ResultSet rs = stmtValida.executeQuery()) {
                    if (rs.next()) {
                        if (rs.getInt("aluno_ok") == 0 || rs.getInt("equip_ok") == 0) {
                            System.err.println("[ERRO]: Aluno ou Equipamento não cadastrado. Operação abortada para manter IDs em ordem.");
                            return false;
                        }
                    }
                }
            }

            // 2. Verifica se o item já está emprestado
            try (PreparedStatement stmtCheck = conn.prepareStatement(sqlCheckDispo)) {
                stmtCheck.setInt(1, emp.getIdEquipamento());
                try (ResultSet rs = stmtCheck.executeQuery()) {
                    if (rs.next() && !rs.getBoolean("disponivel")) {
                        System.out.println("[AVISO]: Este equipamento já está em uso.");
                        return false;
                    }
                }
            }

            // 3. Executa o Empréstimo
            try (PreparedStatement stmtEmp = conn.prepareStatement(sqlEmprestimo);
                 PreparedStatement stmtUp = conn.prepareStatement(sqlUpdate)) {

                stmtEmp.setInt(1, emp.getIdAluno());
                stmtEmp.setInt(2, emp.getIdEquipamento());
                stmtEmp.setString(3, emp.getDataEmprestimo());
                stmtEmp.executeUpdate();

                stmtUp.setInt(1, emp.getIdEquipamento());
                stmtUp.executeUpdate();

                conn.commit();
                System.out.println("Empréstimo realizado com sucesso!");
                return true;
            } catch (SQLException e) {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro de Conexão: " + e.getMessage());
            return false;
        }
    }

    public void registrarDevolucao(int idEquipamento, String dataDevolucao) {
        String sqlEquip = "UPDATE equipamento SET disponivel = TRUE WHERE id = ?";
        String sqlEmp = "UPDATE emprestimo SET data_devolucao = ?, status = 'CONCLUIDO' " +
                "WHERE id_equipamento = ? AND status = 'ATIVO'";

        try (Connection conn = Conexao.getConexao()) {
            if (conn == null) return;
            conn.setAutoCommit(false);

            try (PreparedStatement stmtEquip = conn.prepareStatement(sqlEquip);
                 PreparedStatement stmtEmp = conn.prepareStatement(sqlEmp)) {

                stmtEquip.setInt(1, idEquipamento);
                stmtEquip.executeUpdate();

                stmtEmp.setString(1, dataDevolucao);
                stmtEmp.setInt(2, idEquipamento);
                stmtEmp.executeUpdate();

                conn.commit();
                System.out.println("[SISTEMA]: Devolução concluída e histórico atualizado.");
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Erro ao processar devolução: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}