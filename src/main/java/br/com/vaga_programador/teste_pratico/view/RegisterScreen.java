package br.com.vaga_programador.teste_pratico.view;

import br.com.vaga_programador.teste_pratico.dao.UserDAO;
import br.com.vaga_programador.teste_pratico.model.User;
import br.com.vaga_programador.teste_pratico.util.SecurityUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterScreen extends JFrame {

    private JTextField txtName;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegister;
    private JButton btnCancel;

    public RegisterScreen() {
        setTitle("Cadastro de Usuário");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nome:"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("E-mail:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Senha:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        btnRegister = new JButton("Cadastrar");
        btnCancel = new JButton("Voltar ao Login");

        panel.add(btnCancel);
        panel.add(btnRegister);

        add(panel, BorderLayout.CENTER);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginScreen().setVisible(true);
                dispose();
            }
        });
    }

    private void register() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (name.length() < 3) {
            JOptionPane.showMessageDialog(this, "O nome deve ter no mínimo 3 caracteres.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 8) {
            JOptionPane.showMessageDialog(this, "A senha deve ter no mínimo 8 caracteres.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Expressão regular (Regex) para validar o formato do e-mail (ex: nome@dominio.com)
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailRegex)) {
            JOptionPane.showMessageDialog(this, "E-mail inválido. Digite um formato válido (ex: seuemail@dominio.com).", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();
            String encryptedPassword = SecurityUtil.hashPassword(password);
            User user = new User(name, email, encryptedPassword);
            
            if (userDAO.save(user)) {
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                new LoginScreen().setVisible(true);
                this.dispose();
            }
        } catch (SQLException ex) {
            // Código 23505 é o SQLState padrão do PostgreSQL para violação de constraint (ex: UNIQUE)
            if ("23505".equals(ex.getSQLState())) {
                JOptionPane.showMessageDialog(this, "Não é possível cadastrar. O e-mail informado já existe no sistema.", "E-mail Duplicado", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}
