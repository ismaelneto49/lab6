package sapo.entities;

import sapo.interfaces.Busca;
import sapo.repositories.AtividadeRepository;

public class BuscaAtividade implements Busca {

    private AtividadeRepository atividadeRepository;
    private String[] consulta;

    public BuscaAtividade(AtividadeRepository atividadeRepository, String[] consulta) {
        this.atividadeRepository = atividadeRepository;
        this.consulta = consulta;
    }

    @Override
    public String[] buscar(String[] termos) {
        return this.atividadeRepository.buscar(termos);
    }

    @Override
    public String[] getConsulta() {
        return this.consulta;
    }
}
