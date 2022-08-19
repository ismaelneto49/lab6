package sapo.controllers;

import sapo.entities.BuscaPessoa;
import sapo.entities.HistoricoBusca;
import sapo.interfaces.Busca;
import sapo.services.PessoaService;

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
        Busca buscaPessoa = new BuscaPessoa(this.pessoaService);
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
        return null;
    }

}
