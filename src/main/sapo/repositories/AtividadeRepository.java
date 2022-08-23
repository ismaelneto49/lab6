package sapo.repositories;

import sapo.entities.Atividade;
import sapo.entities.Pessoa;

import java.util.*;
import java.util.stream.Collectors;

public class AtividadeRepository {

    private Map<Pessoa, Map<String, Atividade>> atividades;

    public AtividadeRepository(Map<Pessoa, Map<String, Atividade>> atividades) {
        this.atividades = atividades;
    }

    public boolean contains(String id) {
        for (Map<String, Atividade> atividadesPorPessoa : this.atividades.values()) {
            boolean temAtividadeById = Objects.nonNull(atividadesPorPessoa.get(id));
            if (temAtividadeById) {
                return true;
            }
        }
        return false;
    }

    public Atividade getAtividadeById(String id) {
        for (Map<String, Atividade> atividadesPorPessoa : this.atividades.values()) {
            boolean temAtividadeById = Objects.nonNull(atividadesPorPessoa.get(id));
            if (temAtividadeById) {
                return atividadesPorPessoa.get(id);
            }
        }
        return null;
    }

    public String cadastrarAtividade(String nome, String descricao, Pessoa responsavel) {
        String atividadeId = this.gerarIdAtividade(nome);
        Atividade novaAtividade = new Atividade(atividadeId, nome, descricao, responsavel);
        boolean pessoaNaoTemAtividade = Objects.isNull(this.atividades.get(responsavel));
        if (pessoaNaoTemAtividade) {
            this.atividades.put(responsavel, new HashMap<>());
        }
        this.atividades.get(responsavel).put(atividadeId, novaAtividade);
        return atividadeId;
    }

    public void encerrarAtividade(String id) {
        this.getAtividadeById(id).encerrar();
    }

    public void desativarAtividade(String id) {
        this.getAtividadeById(id).desativar();
    }

    public void reabrirAtividade(String id) {
        this.getAtividadeById(id).reabrir();
    }

    public String exibirAtividade(String id) {
        return this.getAtividadeById(id).toString();
    }

    public void alterarDescricaoAtividade(String id, String descricao) {
        this.getAtividadeById(id).setDescricao(descricao);
    }

    public void alterarResponsavelAtividade(String id, Pessoa novoResponsavel) {
        this.getAtividadeById(id).setResponsavel(novoResponsavel);
    }

    private String gerarIdAtividade(String nome) {
        StringBuilder consoantesBuilder = new StringBuilder();
        int numeroNovaAtividade = this.calcularQuantidadeAtividades();
        final String CONSOANTES = "bcdfghjklmnpqrstvwxyz";

        for (int i = 0; i < nome.length(); i++) {
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

    public String[] buscar(String[] termos) {
        List<String> ondeBuscar = new ArrayList<>();
        for (Map<String, Atividade> map : this.atividades.values()) {
            ondeBuscar.addAll(map.keySet());
            ondeBuscar.addAll(map.values().stream().map(a -> a.getId() + "__" + a.getNome().toLowerCase()).collect(Collectors.toList()));
            ondeBuscar.addAll(map.values().stream().map(a -> a.getId() + "__" + a.getDescricao().toLowerCase()).collect(Collectors.toList()));
        }

        Set<String> resultado = new HashSet<>();
        for (String termo : termos) {
            for (String busca : ondeBuscar) {
                if (busca.contains(termo)) {
                    if (busca.length() > termo.length()) {
                        resultado.add(this.getAtividadeById(busca.split("__")[0]).toString());
                    } else {
                        resultado.add(this.getAtividadeById(busca).toString());
                    }
                }
            }
        }
        String[] retorno = resultado.toArray(new String[]{});
        Arrays.sort(retorno);
        return retorno;
    }
}
