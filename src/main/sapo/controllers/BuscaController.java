package sapo.controllers;

import sapo.entities.*;
import sapo.interfaces.Busca;
import sapo.repositories.PessoaRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BuscaController {

    private PessoaRepository pessoaRepository;
    private HistoricoBusca historicoBusca;
//    private AtividadeService atividadeService;
//    private TarefaService tarefaService;


    public BuscaController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.historicoBusca = new HistoricoBusca();
    }

    public String[] exibirPessoas(String consulta) {
        Busca buscaPessoa = new BuscaPessoa(this.pessoaRepository, consulta.split(" "));
        String[] resultado = buscaPessoa.buscar(consulta.split(" "));
        this.historicoBusca.save(buscaPessoa);
        return resultado;
    }

    public String[] buscarAtividade(String consulta) {
        return null;
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
