package br.edu.ifpa.laboratorio.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    // Endereço do banco, usuário (root) e a senha (12345)
    private static final String URL = "jdbc:mysql://localhost:3306/controle_laboratorio";
    private static final String USER = "root";
    private static final String PASS = "12345";

    public static Connection getConexao() throws SQLException {
        try {
            // Isso avisa o Java para usar o Driver do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado! Verifique o pom.xml.", e);
        }
    }
}