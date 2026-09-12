package br.com.vaga_programador.teste_pratico.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    // Configuração do banco de dados - ajuste se necessário
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234"; // ou 'postgres'

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            
            // Criação da tabela de usuários
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) UNIQUE NOT NULL, " +
                    "password VARCHAR(256) NOT NULL)";
            stmt.execute(createUsersTable);

            // Criação da tabela de funcionários
            String createEmployeesTable = "CREATE TABLE IF NOT EXISTS employees (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "admission_date DATE NOT NULL, " +
                    "salary NUMERIC(10, 2) NOT NULL, " +
                    "status BOOLEAN NOT NULL)";
            stmt.execute(createEmployeesTable);

            System.out.println("Tabelas inicializadas com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao inicializar o banco de dados. Verifique a conexao e as credenciais.");
            e.printStackTrace();
        }
    }
}
