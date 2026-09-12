/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.com.vaga_programador.teste_pratico;

import br.com.vaga_programador.teste_pratico.util.DatabaseConnection;
import br.com.vaga_programador.teste_pratico.view.LoginScreen;

import javax.swing.SwingUtilities;

public class Teste_pratico {

    public static void main(String[] args) {
        // Inicializa o banco de dados (cria tabelas se não existirem)
        DatabaseConnection.initializeDatabase();

        // Inicia a interface gráfica
        SwingUtilities.invokeLater(() -> {
            new LoginScreen().setVisible(true);
        });
    }
}
