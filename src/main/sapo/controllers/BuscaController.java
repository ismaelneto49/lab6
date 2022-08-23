package sapo.controllers;

import sapo.entities.BuscaAtividade;
import sapo.entities.BuscaPessoa;
import sapo.entities.HistoricoBusca;
import sapo.interfaces.Busca;
import sapo.repositories.AtividadeRepository;
import sapo.repositories.PessoaRepository;
import sapo.repositories.TarefaRepository;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BuscaController {

    private PessoaRepository pessoaRepository;
    private HistoricoBusca historicoBusca;
    private AtividadeRepository atividadeRepository;
    private TarefaRepository tarefaRepository;


    public BuscaController(PessoaRepository pessoaRepository, AtividadeRepository atividadeRepository, TarefaRepository tarefaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.atividadeRepository = atividadeRepository;
        this.tarefaRepository = tarefaRepository;
        this.historicoBusca = new HistoricoBusca();
    }

    public String[] exibirPessoas(String consulta) {
        Busca buscaPessoa = new BuscaPessoa(this.pessoaRepository, consulta.split(" "));
        String[] resultado = buscaPessoa.buscar(consulta.split(" "));
        this.historicoBusca.save(buscaPessoa);
        return resultado;
    }

    public String[] buscarAtividade(String consulta) {
        Busca buscaAtividade = new BuscaAtividade(this.atividadeRepository, consulta.split(" "));
        String[] resultado = buscaAtividade.buscar(consulta.split(" "));
        this.historicoBusca.save(buscaAtividade);
        return resultado;
    }

    public String[] buscarTarefas(String nome) {
        return null;
    }

    public String[] buscarTarefas(String idAtividade, String nome) {
        return null;
    }

    public String[] sugerirTarefas(String id, String cpf) {
        return null;
    }

    public String[] buscasMaisRecentes(int nBuscas) {
        String[] resultado = Arrays.asList(this.historicoBusca.buscasMaisRecentes(nBuscas))
                .stream()
                .map(b -> b.replaceAll("_", " ").trim())
                .collect(Collectors.toList())
                .toArray(new String[]{});
        return resultado;
    }

    public String[] exibirHistoricoBusca(int indexBusca) {
        return this.historicoBusca.exibirBusca(indexBusca);
    }
}
