package br.com.neurotech.services;

import br.com.neurotech.interfaces.MySQLConnectionService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionImp implements MySQLConnectionService {

    /**
     * Classe criada para criar a camada de comunicação com o banco de dados
     */

    // Configurações de conexão
    private static final String URL = "jdbc:mysql://localhost:3306/neurotech";
    private static final String USER = "root";
    private static final String PASSWORD = "megazord123";

    // Mantém uma única instância da conexão
    private static Connection connection = null;

    @Override
    public Connection GetConnection() throws SQLException {
        // Se a conexão ainda não estiver aberta, abre uma nova conexão
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    @Override
    public void CloseConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
