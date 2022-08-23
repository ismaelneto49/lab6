package sapo.entities;

import sapo.interfaces.Busca;
import sapo.repositories.PessoaRepository;
import sapo.repositories.TarefaRepository;

public class BuscaSugestao implements Busca {

    private TarefaRepository tarefaRepository;
    private PessoaRepository pessoaRepository;
    public String[] consulta;

    public BuscaSugestao(TarefaRepository tarefaRepository, PessoaRepository pessoaRepository, String[] consulta) {
        this.tarefaRepository = tarefaRepository;
        this.pessoaRepository = pessoaRepository;
        this.consulta = consulta;
    }

    @Override
    public String[] buscar(String[] termos) {
        Pessoa p = this.pessoaRepository.getPessoaByCpf(termos[0]);
        return this.tarefaRepository.sugerir(p);
    }

    @Override
    public String[] getConsulta() {
        return this.consulta;
    }
}
