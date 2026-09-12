package br.com.vaga_programador.teste_pratico.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private int id;
    private String name;
    private LocalDate admissionDate;
    private BigDecimal salary;
    private boolean status;

    public Employee() {}

    public Employee(String name, LocalDate admissionDate, BigDecimal salary, boolean status) {
        this.name = name;
        this.admissionDate = admissionDate;
        this.salary = salary;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
}
