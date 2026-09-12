package br.com.vaga_programador.teste_pratico.dao;

import br.com.vaga_programador.teste_pratico.model.Employee;
import br.com.vaga_programador.teste_pratico.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    
    public boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO employees (name, admission_date, salary, status) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, employee.getName());
            stmt.setDate(2, java.sql.Date.valueOf(employee.getAdmissionDate()));
            stmt.setBigDecimal(3, employee.getSalary());
            stmt.setBoolean(4, employee.isStatus());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setAdmissionDate(rs.getDate("admission_date").toLocalDate());
                employee.setSalary(rs.getBigDecimal("salary"));
                employee.setStatus(rs.getBoolean("status"));
                employees.add(employee);
            }
        }
        return employees;
    }
}
