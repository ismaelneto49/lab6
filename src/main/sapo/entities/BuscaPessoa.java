package sapo.entities;

import sapo.interfaces.Busca;
import sapo.services.PessoaService;

public class BuscaPessoa implements Busca {

    private PessoaService pessoaService;
    private String[] consulta;

    public BuscaPessoa(PessoaService pessoaService, String[] consulta) {
        this.pessoaService = pessoaService;
        this.consulta = consulta;
    }

    @Override
    public String[] buscar(String[] termos) {
        return this.pessoaService.buscar(termos);
    }

    @Override
    public String[] getConsulta() {
        return this.consulta;
    }
}
