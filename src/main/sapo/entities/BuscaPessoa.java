package sapo.entities;

import sapo.interfaces.Busca;
import sapo.services.PessoaService;

public class BuscaPessoa implements Busca {

    private PessoaService pessoaService;

    public BuscaPessoa(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    public String[] buscar(String[] termos) {
        return this.pessoaService.buscar(termos);
    }
}
