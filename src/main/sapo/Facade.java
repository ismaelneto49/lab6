package sapo;

import sapo.controllers.AtividadeController;
import sapo.entities.Atividade;

import java.util.HashMap;
import java.util.Optional;

public class Facade {

    private AtividadeController atividadeController;

    public Facade() {
        this.atividadeController = new AtividadeController(new HashMap<>());
    }

    public String cadastrarAtividade(String nome, String descricao, String cpf) {
        return this.atividadeController.cadastrarAtividade(nome, descricao, cpf);
    }

    public Optional<Atividade> recuperarAtividade(String id){
        return this.atividadeController.recuperarAtividade(id);
    }

    public void encerrarAtividade(String id) {
        this.atividadeController.encerrarAtividade(id);
    }

    public void desativarAtividade(String id) {
        this.atividadeController.desativarAtividade(id);
    }
    public void reabrirAtividade(String id) {
        this.atividadeController.reabrirAtividade(id);
    }

    public String exibirAtividade(String id) {
        return this.atividadeController.exibirAtividade(id);
    }

    public void alterarDescricaoAtividade(String id, String descricao) {
        this.atividadeController.alterarDescricaoAtividade(id, descricao);
    }

    public void alterarResponsavelAtividade(String id, String cpf) {
        this.atividadeController.alterarResponsavelAtividade(id, cpf);
    }


    }