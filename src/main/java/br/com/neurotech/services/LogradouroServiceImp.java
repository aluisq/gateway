package br.com.neurotech.services;

import br.com.neurotech.interfaces.LogradouroService;
import br.com.neurotech.interfaces.MySQLConnectionService;
import br.com.neurotech.models.LogradouroDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.sql.*;

import br.com.neurotech.utils.ExcecaoCepNaoExiste;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.implementation.bytecode.Throw;


public class LogradouroServiceImp implements LogradouroService {

    @Override
    public LogradouroDTO BuscarLogradouroBrasilApi(int cep) {

        // Cria um cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Cria uma solicitação GET para uma URL específica
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://brasilapi.com.br/api/cep/v1/" + cep))
                .build();

        // Envia a solicitação e processa a resposta
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica se o código de status da resposta é diferente de 200
            if (response.statusCode() != 200) {
                return null;
            }

            // Converte a resposta para um objeto User usando Jackson ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            LogradouroDTO logradouroDTO = mapper.readValue(response.body(), LogradouroDTO.class);

            return logradouroDTO;

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public int SalvarLogradouroBanco(LogradouroDTO model) throws ExcecaoCepNaoExiste {

        if (model == null){

            throw new ExcecaoCepNaoExiste("CEP Não existe!");
        }

        int idRegistro = 0;

        // Se número igual a zero não salvou no banco;

        MySQLConnectionImp mySQLConnectionService = new MySQLConnectionImp();

        try {

            Connection connection=  mySQLConnectionService.GetConnection();

            // Cria uma instrução SQL INSERT
            String sql = "INSERT INTO tab_logradouros (CEP, UF, CIDADE, VIZINHANCA, RUA) VALUES (?,?,?,?,?)";

            // Configurando um PreparedStatement
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, model.getCep());
            stmt.setString(2, model.getState());
            stmt.setString(3, model.getCity());
            stmt.setString(4, model.getNeighborhood());
            stmt.setString(5, model.getStreet());

            ResultSet rs = null;

            // Executa a instrução INSERT
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {

                // Recupera o ID do registro salvo
                rs = stmt.getGeneratedKeys();

                // Seta o id
                if ( rs.next()){
                    idRegistro = rs.getInt(1);

                    System.out.println("ID " + idRegistro +" - CEP INSERIDO COM SUCESSO NO BANCO!");
                }

            } else {
                System.out.println("ERRO NA TENTATIVA DE INSERIR O CEP NO BANCO DE DADOS");
            }

            // Fechando o statement
            stmt.close();
            //Encerrando conexao
            mySQLConnectionService.CloseConnection();

            return idRegistro;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LogradouroDTO RecuperarLogradouroBanco(int idRegistroLogradouro) {

        LogradouroDTO model = null;

        MySQLConnectionImp mySQLConnectionService = new MySQLConnectionImp();

        try {

            Connection connection=  mySQLConnectionService.GetConnection();

            String sql = "SELECT * FROM tab_logradouros WHERE id = ?";

            // Configurando um PreparedStatement
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idRegistroLogradouro);


            ResultSet rs = null;

            rs = stmt.executeQuery();

            if(rs.next()){

                model = new LogradouroDTO();

                model.setCep(rs.getString("CEP"));
                model.setState(rs.getString("UF"));
                model.setCity(rs.getString("CIDADE"));
                model.setNeighborhood(rs.getString("VIZINHANCA"));
                model.setStreet(rs.getString("RUA"));
            }

            // Fechando o statement
            stmt.close();
            //Encerrando conexao
            mySQLConnectionService.CloseConnection();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return model;
    }

    @Override
    public void MostrarLogradouroConsole(LogradouroDTO model) {

        // Validando o model
        if (model == null)
            System.out.println("Erro ao tentar exibir um registro do banco");


        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("CEP | ").append(model.getCep()).append("\n");
        sb.append("UF | ").append(model.getState()).append("\n");
        sb.append("CIDADE | ").append(model.getCity()).append("\n");
        sb.append("VIZINHANCA | ").append(model.getNeighborhood()).append("\n");
        sb.append("RUA | ").append(model.getStreet()).append("\n");

        System.out.println(sb);

    }

}
