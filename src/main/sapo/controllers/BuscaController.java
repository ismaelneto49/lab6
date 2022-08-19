package sapo.controllers;

import sapo.services.PessoaService;

public class BuscaController {

    private PessoaService pessoaService;
//    private AtividadeService atividadeService;
//    private TarefaService tarefaService;


    public BuscaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    public String[] exibirPessoas(String consulta) {
        return this.pessoaService.buscar(consulta.split(" "));
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
