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

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "E-mail inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
