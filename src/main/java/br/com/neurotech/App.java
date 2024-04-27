package br.com.neurotech;

import br.com.neurotech.interfaces.LogradouroService;
import br.com.neurotech.models.LogradouroDTO;
import br.com.neurotech.services.LogradouroServiceImp;
import br.com.neurotech.utils.ExcecaoCepNaoExiste;

import java.util.Scanner;

public class App
{
    public static void main( String[] args ) throws ExcecaoCepNaoExiste {

        // Cria um objeto Scanner para ler entrada do console
        Scanner scanner = new Scanner(System.in);

        // Variável de Controle
        boolean cepIsValid = false;

        // Enquanto o CEP não for apenas números será reiniciado a pergunta do CEP
        while(!cepIsValid){

            // Solicita ao usuário que insira um valor
            System.out.println( "INFORME O CEP:" );

            // Armazena o valor inputado
            String cep = scanner.nextLine();

            // Verifica se a entrada contém apenas números
            if (cep.matches("[0-9]+")) {

                // Variável de controle alterada para sair do While
                cepIsValid = true;

                // Converte a entrada para um número inteiro
                int cepNumber = Integer.parseInt(cep);

                // Instanciação da classe que contém todos os serviços relacionado ao CEP
                LogradouroServiceImp logradouroServiceImp = new LogradouroServiceImp();

                // Consulta o CEP na API
                LogradouroDTO logradouroDTO = logradouroServiceImp.BuscarLogradouroBrasilApi(cepNumber);

                //Salva Registro no Banco
                int idRegistroLogradouro = logradouroServiceImp.SalvarLogradouroBanco(logradouroDTO);

                // Recupera Logradouro do Banco
                LogradouroDTO logradouroDTORetornoBanco =  logradouroServiceImp.RecuperarLogradouroBanco(idRegistroLogradouro);

                logradouroServiceImp.MostrarLogradouroConsole(logradouroDTORetornoBanco);


            } else {
                // Imprime uma mensagem de erro se a entrada não for um número
                System.out.println("Entrada inválida. Por favor, digite apenas números.");
            }
        }
        scanner.close();
    }
}
