package sapo.controllers;

import sapo.entities.Atividade;
import sapo.entities.Pessoa;
import sapo.entities.Tarefa;
import sapo.entities.TarefaGerencial;
import sapo.repositories.AtividadeRepository;
import sapo.repositories.PessoaRepository;
import sapo.repositories.TarefaRepository;

import java.util.*;

public class TarefaController {

    private PessoaRepository pessoaRepository;
    private AtividadeRepository atividadeRepository;
    private TarefaRepository tarefaRepository;

    public TarefaController(PessoaRepository pessoaRepository, AtividadeRepository atividadeRepository, TarefaRepository tarefaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.atividadeRepository = atividadeRepository;
        this.tarefaRepository = tarefaRepository;
    }

    public String cadastrarTarefa(String atividadeId, String nome, String[] habilidades) {
        this.validarParametro(atividadeId, "ID da Atividade");
        this.validarParametro(nome, "nome");
        this.validarIdAtividade(atividadeId);
        Atividade atividade = this.atividadeRepository.getAtividadeById(atividadeId);
        return this.tarefaRepository.cadastrarTarefa(atividade, nome, habilidades);

    }

    public void alterarNomeTarefa(String idTarefa, String novoNome) {
        this.validarParametro(idTarefa, "ID da Tarefa");
        this.validarParametro(novoNome, "Novo nome");
        this.validarIdTarefa(idTarefa);
        this.tarefaRepository.alterarNomeTarefa(idTarefa, novoNome);

    }

    public void alterarHabilidadesTarefa(String idTarefa, String[] habilidades) {
        this.validarParametro(idTarefa, "ID da Tarefa");
        this.validarIdTarefa(idTarefa);
        this.tarefaRepository.alterarHabilidadesTarefa(idTarefa, habilidades);
    }

    public void adicionarHorasTarefa(String idTarefa, int horas) {
        this.validarParametro(idTarefa, "ID da Tarefa");
        this.validarIdTarefa(idTarefa);
        this.tarefaRepository.adicionarHorasTarefa(idTarefa, horas);
    }

    public void removerHorasTarefa(String idTarefa, int horas) {
        this.validarIdTarefa(idTarefa);
        this.tarefaRepository.removerHorasTarefa(idTarefa, horas);
    }

    public void concluirTarefa(String idTarefa) {
        this.validarIdTarefa(idTarefa);
        this.tarefaRepository.concluirTarefa(idTarefa);
    }

    public String exibirTarefa(String idTarefa) {
        this.validarIdTarefa(idTarefa);
        return this.tarefaRepository.exibirTarefa(idTarefa);
    }

    public void associarPessoaTarefa(String cpf, String idTarefa) {
        this.validarContemCpf(cpf);
        this.validarIdTarefa(idTarefa);
        Pessoa pessoa = this.pessoaRepository.getPessoaByCpf(cpf);
        this.tarefaRepository.associarPessoaTarefa(pessoa, idTarefa);

    }

    public void removerPessoaTarefa(String cpf, String idTarefa) {
        this.validarContemCpf(cpf);
        this.validarIdTarefa(idTarefa);
        Pessoa pessoa = this.pessoaRepository.getPessoaByCpf(cpf);
        this.tarefaRepository.removerPessoaTarefa(pessoa, idTarefa);
    }


    public String cadastrarTarefaGerencial(String atividadeId, String nome, String[] habilidades, String[] idTarefas) {
        this.validarParametro(atividadeId, "ID da Atividade");
        this.validarParametro(nome, "Nome");
        this.validarIdAtividade(atividadeId);

        Atividade atividade = this.atividadeRepository.getAtividadeById(atividadeId);

        for (String idTarefa : idTarefas) {
            this.validarIdTarefa(idTarefa);
        }

        return this.tarefaRepository.cadastrarTarefaGerencial(atividade, nome, habilidades, idTarefas);

    }

    public void adicionarNaTarefaGerencial(String idTarefaGerencial, String idTarefa) {
        this.validarIdTarefa(idTarefa);
        this.validarIdTarefaGerencial(idTarefaGerencial);
        this.tarefaRepository.adicionarNaTarefaGerencial(idTarefaGerencial, idTarefa);
    }

    public void removerDaTarefaGerencial(String idTarefaGerencial, String idTarefa) {
        this.validarIdTarefa(idTarefa);
        this.validarIdTarefaGerencial(idTarefaGerencial);
        this.tarefaRepository.removerDaTarefaGerencial(idTarefaGerencial, idTarefa);
    }

    public int contarTodasTarefasNaTarefaGerencial(String idTarefaGerencial) {
        this.validarIdTarefaGerencial(idTarefaGerencial);
        return tarefaRepository.contarTodasTarefasNaTarefaGerencial(idTarefaGerencial);
    }

    private void validarParametro(String parametro, String nomeParametro) {
        if (Objects.isNull(parametro) || parametro.isBlank()) {
            throw new IllegalArgumentException("Campo " + nomeParametro + " não pode ser nulo ou vazio.");
        }
    }

    private void validarContemCpf(String cpf) {
        if (!this.pessoaRepository.contains(cpf)) {
            throw new NoSuchElementException("CPF fornecido não pertence a nenhuma Pessoa");
        }
    }

    private void validarIdAtividade(String id) {
        if (!this.atividadeRepository.contains(id)) {
            throw new NoSuchElementException("ID fornecido não pertence a nenhuma Atividade");
        }
    }

    private Tarefa validarIdTarefa(String id) {
        if (this.tarefaRepository.containsTarefa(id)) {
            return this.tarefaRepository.getTarefaById(id);
        }

        throw new NoSuchElementException("O id passado não pertence a nenhuma atividade");
    }

    private TarefaGerencial validarIdTarefaGerencial(String id) {
        if (this.tarefaRepository.containsTarefaGerencial(id)) {
            return this.tarefaRepository.getTarefaGerencialById(id);
        }
        throw new NoSuchElementException("O id passado não pertence a nenhuma atividade gerencial");
    }


}
