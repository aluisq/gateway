package br.com.neurotech.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface MySQLConnectionService {

    Connection GetConnection() throws SQLException;

    void CloseConnection() throws SQLException;
}
