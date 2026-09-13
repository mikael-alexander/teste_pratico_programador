package br.com.vaga_programador.teste_pratico.view;

import br.com.vaga_programador.teste_pratico.dao.EmployeeDAO;
import br.com.vaga_programador.teste_pratico.model.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EmployeeScreen extends JFrame {

    private JTextField txtName;
    private JTextField txtAdmissionDate;
    private JTextField txtSalary;
    private JCheckBox chkStatus;
    private JButton btnSave;
    private JTable table;
    private DefaultTableModel tableModel;

    public EmployeeScreen() {
        setTitle("Cadastro de Funcionários");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Nome:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Data de Admissão (dd/MM/yyyy):"));
        txtAdmissionDate = new JTextField();
        formPanel.add(txtAdmissionDate);

        formPanel.add(new JLabel("Salário:"));
        txtSalary = new JTextField();
        formPanel.add(txtSalary);

        formPanel.add(new JLabel("Status Ativo:"));
        chkStatus = new JCheckBox();
        chkStatus.setSelected(true);
        formPanel.add(chkStatus);

        btnSave = new JButton("Salvar");
        formPanel.add(new JLabel("")); // spacer
        formPanel.add(btnSave);

        add(formPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Data Admissão", "Salário", "Status"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEmployee();
            }
        });

        loadEmployees();
    }

    private void saveEmployee() {
        String name = txtName.getText().trim();
        String dateStr = txtAdmissionDate.getText().trim();
        String salaryStr = txtSalary.getText().trim();
        boolean status = chkStatus.isSelected();

        if (name.isEmpty() || dateStr.isEmpty() || salaryStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (name.length() < 3) {
            JOptionPane.showMessageDialog(this, "O nome deve ter no mínimo 3 caracteres.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate admissionDate = LocalDate.parse(dateStr, formatter);
            
            BigDecimal salary = new BigDecimal(salaryStr.replace(",", "."));

            Employee employee = new Employee(name, admissionDate, salary, status);
            EmployeeDAO employeeDAO = new EmployeeDAO();
            
            if (employeeDAO.save(employee)) {
                JOptionPane.showMessageDialog(this, "Funcionário salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                loadEmployees();
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Salário inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar no banco de dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void loadEmployees() {
        try {
            EmployeeDAO employeeDAO = new EmployeeDAO();
            List<Employee> employees = employeeDAO.getAll();
            
            tableModel.setRowCount(0); // Clear table
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            for (Employee emp : employees) {
                tableModel.addRow(new Object[]{
                        emp.getId(),
                        emp.getName(),
                        emp.getAdmissionDate().format(formatter),
                        emp.getSalary(),
                        emp.isStatus() ? "Ativo" : "Inativo"
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar funcionários: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtAdmissionDate.setText("");
        txtSalary.setText("");
        chkStatus.setSelected(true);
    }
}
