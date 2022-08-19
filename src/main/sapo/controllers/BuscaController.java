package sapo.controllers;

import sapo.entities.*;
import sapo.interfaces.Busca;
import sapo.services.PessoaService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuscaController {

    private PessoaService pessoaService;
    private HistoricoBusca historicoBusca;
//    private AtividadeService atividadeService;
//    private TarefaService tarefaService;


    public BuscaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
        this.historicoBusca = new HistoricoBusca();
    }

    public String[] exibirPessoas(String consulta) {
        Busca buscaPessoa = new BuscaPessoa(this.pessoaService, consulta.split(" "));
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
        return null;
    }

    public String[] exibirHistoricoBusca(int indexBusca) {
        Busca busca = this.historicoBusca.getBuscaById(indexBusca);
        List<String> retorno = new ArrayList<>();

        String tipo = "";
        if (busca.getClass() == BuscaPessoa.class) {
            tipo = "PESSOA";
        } else if (busca.getClass() == BuscaAtividade.class) {
            tipo = "ATIVIDADE";
        } else if (busca.getClass() == BuscaTarefa.class) {
            tipo = "TAREFA";
        } else {
            tipo = "SUGESTÃO";
        }
        retorno.add(tipo);
        retorno.addAll(Arrays.asList(busca.buscar(busca.getConsulta())));
        return retorno.toArray(new String[]{});
    }

}
