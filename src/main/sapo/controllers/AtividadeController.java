package sapo.controllers;

import sapo.entities.Atividade;
import sapo.entities.Pessoa;

import java.util.*;

public class AtividadeController {
    private Map<Pessoa, Map<String, Atividade>> atividades;
    private PessoaController pessoaController;

    public AtividadeController (Map<Pessoa, Map<String, Atividade>> atividades, PessoaController pessoaController) {
        this.atividades = atividades;
        this.pessoaController = pessoaController;
    }
    
    public String cadastrarAtividade(String nome, String descricao, String cpf) {
        this.validarParametro(nome, "nome");
        this.validarParametro(descricao, "descrição");
        this.validarParametro(cpf, "CPF");
        Pessoa responsavel = this.pessoaController.recuperarPessoa(cpf);
        String atividadeId = this.gerarIdAtividade(nome);
        Atividade novaAtividade = new Atividade(atividadeId, nome, descricao, responsavel);
        boolean pessoaNaoTemAtividade = Objects.isNull(this.atividades.get(responsavel));
        if (pessoaNaoTemAtividade) {
            this.atividades.put(responsavel, new HashMap<>());
        }
        this.atividades.get(responsavel).put(atividadeId, novaAtividade);
        return atividadeId;
    }

    public Optional<Atividade> recuperarAtividade(String id) {
        for (Map<String, Atividade> atividadesPorPessoa : this.atividades.values()) {
            if (Objects.nonNull(atividadesPorPessoa.get(id))) {
                return Optional.of(atividadesPorPessoa.get(id));
            }
        }
        return Optional.empty();
    }

    public void encerrarAtividade(String id) {
        Atividade atividade = this.validarIdAtividade(id);
        atividade.encerrar();
    }

    public void desativarAtividade(String id) {
        Atividade atividade = this.validarIdAtividade(id);
        atividade.desativar();
    }

    public void reabrirAtividade(String id) {
        Atividade atividade = this.validarIdAtividade(id);
        atividade.reabrir();
    }

    public String exibirAtividade(String id) {
        Atividade atividade = this.validarIdAtividade(id);
        return atividade.toString();
    }

    public void alterarDescricaoAtividade(String id, String descricao) {
        this.validarParametro(descricao, "descrição");
        Atividade atividade = this.validarIdAtividade(id);
        atividade.reabrir();
    }

    public void alterarResponsavelAtividade(String id, String cpf) {
        this.validarParametro(cpf, "cpf");
        Atividade atividade = this.validarIdAtividade(id);
        Pessoa novoResponsavel = this.pessoaController.recuperarPessoa(cpf);
        atividade.setResponsavel(novoResponsavel);
    }

    private String gerarIdAtividade(String nome) {
        StringBuilder consoantesBuilder = new StringBuilder();
        int numeroNovaAtividade = this.calcularQuantidadeAtividades();
        final String CONSOANTES = "bcdfghjklmnpqrstvwxyz";

        for (int i = 0; i < nome.length(); i ++) {
            String caractere = Character.toString(nome.charAt(i));
            if (CONSOANTES.contains(caractere.toLowerCase())) {
                consoantesBuilder.append(caractere);
            }
        }
        String consoantesFallback = new String(new char[3]).replace("\0", "X");
        consoantesBuilder.append(consoantesFallback);
        String consoantesId = consoantesBuilder.toString().substring(0, 3).toUpperCase();
        return consoantesId + "-" + numeroNovaAtividade;
    }

    private int calcularQuantidadeAtividades() {
        int quantidadeAtividades = 0;
        for (Map<String, Atividade> atividadesPorPessoa : this.atividades.values()) {
            quantidadeAtividades += atividadesPorPessoa.size();
        }
        return quantidadeAtividades;
    }
    private void validarParametro(String parametro, String nomeParametro) {
        if (Objects.isNull(parametro) || parametro.isBlank()) {
            throw new IllegalArgumentException("Campo " + nomeParametro + " não pode ser nulo ou vazio.");
        }
    }

    public Atividade validarIdAtividade(String id) {
        Optional<Atividade> optionalAtividade = this.recuperarAtividade(id);
        if (optionalAtividade.isEmpty()) {
            throw new NoSuchElementException("O id passado não pertence a nenhuma atividade");
        }
        return optionalAtividade.get();
    }
}
