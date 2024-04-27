package br.com.neurotech.interfaces;

import br.com.neurotech.models.LogradouroDTO;
import br.com.neurotech.services.LogradouroServiceImp;
import br.com.neurotech.utils.ExcecaoCepNaoExiste;

public interface LogradouroService {

    LogradouroDTO BuscarLogradouroBrasilApi(int cep);
    int SalvarLogradouroBanco(LogradouroDTO model) throws ExcecaoCepNaoExiste;
    LogradouroDTO RecuperarLogradouroBanco(int idRegistroLogradouro);

    void MostrarLogradouroConsole(LogradouroDTO model);

}
