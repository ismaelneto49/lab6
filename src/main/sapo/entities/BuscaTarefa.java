package sapo.entities;

import sapo.interfaces.Busca;
import sapo.repositories.AtividadeRepository;
import sapo.repositories.TarefaRepository;

public class BuscaTarefa implements Busca {

    private TarefaRepository tarefaRepository;
    private AtividadeRepository atividadeRepository;
    public String[] consulta;

    public BuscaTarefa(TarefaRepository tarefaRepository, AtividadeRepository atividadeRepository, String[] consulta) {
        this.tarefaRepository = tarefaRepository;
        this.atividadeRepository = atividadeRepository;
        this.consulta = consulta;
    }

    @Override
    public String[] buscar(String[] termos) {
        if (termos.length == 1) {
            return this.tarefaRepository.buscar(termos[0]);
        }
        Atividade a = this.atividadeRepository.getAtividadeById(termos[0]);
        return this.tarefaRepository.buscar(a, termos[1]);
    }

    @Override
    public String[] getConsulta() {
        return this.consulta;
    }
}
