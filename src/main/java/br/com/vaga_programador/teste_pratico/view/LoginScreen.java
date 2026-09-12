package br.com.vaga_programador.teste_pratico.view;

import br.com.vaga_programador.teste_pratico.dao.UserDAO;
import br.com.vaga_programador.teste_pratico.model.User;
import br.com.vaga_programador.teste_pratico.util.SecurityUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginScreen extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    public LoginScreen() {
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("E-mail:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Senha:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        btnLogin = new JButton("Login");
        btnRegister = new JButton("Cadastrar");

        panel.add(btnRegister);
        panel.add(btnLogin);

        add(panel, BorderLayout.CENTER);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterScreen().setVisible(true);
                dispose();
            }
        });
    }

    private void login() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();
            String encryptedPassword = SecurityUtil.hashPassword(password);
            User user = userDAO.login(email, encryptedPassword);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                new EmployeeScreen().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "E-mail ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro de conexão com o banco de dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
