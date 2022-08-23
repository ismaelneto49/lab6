package sapo.repositories;

import sapo.entities.Atividade;
import sapo.entities.Pessoa;
import sapo.entities.Tarefa;
import sapo.entities.TarefaGerencial;

import java.util.*;

public class TarefaRepository {

    private Map<Atividade, Map<String, Tarefa>> tarefas;
    private Map<Atividade, Map<String, TarefaGerencial>> tarefasGerenciais;

    public TarefaRepository(Map<Atividade, Map<String, Tarefa>> tarefas, Map<Atividade, Map<String, TarefaGerencial>> tarefasGerenciais) {
        this.tarefas = tarefas;
        this.tarefasGerenciais = tarefasGerenciais;
    }

    public boolean containsTarefa(String id) {
        for (Map<String, Tarefa> tarefasPorAtividade : this.tarefas.values()) {
            boolean temAtividadeById = Objects.nonNull(tarefasPorAtividade.get(id));
            if (temAtividadeById) {
                return true;
            }
        }
        return false;
    }

    public boolean containsTarefaGerencial(String id) {
        for (Map<String, TarefaGerencial> tarefasGerenciaisPorAtividade : this.tarefasGerenciais.values()) {
            boolean temAtividadeGerencialById = Objects.nonNull(tarefasGerenciaisPorAtividade.get(id));
            if (temAtividadeGerencialById) {
                return true;
            }
        }
        return false;
    }

    public Tarefa getTarefaById(String id) {
        for (Map<String, Tarefa> tarefasPorAtividade : this.tarefas.values()) {
            boolean temTarefaById = Objects.nonNull(tarefasPorAtividade.get(id));
            if (temTarefaById) {
                return tarefasPorAtividade.get(id);
            }
        }
        return null;
    }

    public TarefaGerencial getTarefaGerencialById(String id) {
        for (Map<String, TarefaGerencial> tarefasGerenciaisPorAtividade : this.tarefasGerenciais.values()) {
            boolean temAtividadeGerencialById = Objects.nonNull(tarefasGerenciaisPorAtividade.get(id));
            if (temAtividadeGerencialById) {
                return tarefasGerenciaisPorAtividade.get(id);
            }
        }
        return null;
    }

    public String cadastrarTarefa(Atividade atividade, String nome, String[] habilidades) {
        int numeroNovaTarefa = this.calcularQuantidadeTarefas();
        String idNovaTarefa = atividade.getId() + "-" + numeroNovaTarefa;
        Tarefa novaTarefa = new Tarefa(idNovaTarefa, nome, 0, atividade, habilidades);
        atividade.cadastrarTarefa(novaTarefa);
        boolean atividadeNaoTemTarefa = Objects.isNull(this.tarefas.get(atividade));

        if (atividadeNaoTemTarefa) {
            this.tarefas.put(atividade, new HashMap<>());
        }
        this.tarefas.get(atividade).put(idNovaTarefa, novaTarefa);

        return idNovaTarefa;
    }

    public void alterarNomeTarefa(String idTarefa, String novoNome) {
        this.getTarefaById(idTarefa).setNome(novoNome);

    }

    public void alterarHabilidadesTarefa(String idTarefa, String[] habilidades) {
        this.getTarefaById(idTarefa).setHabilidades(habilidades);

    }

    public void adicionarHorasTarefa(String idTarefa, int horas) {
        this.getTarefaById(idTarefa).adicionarHoras(horas);
    }

    public void removerHorasTarefa(String idTarefa, int horas) {
        this.getTarefaById(idTarefa).removerHoras(horas);

    }

    public void concluirTarefa(String idTarefa) {
        this.getTarefaById(idTarefa).concluirTarefa();
    }

    public String exibirTarefa(String idTarefa) {
        return this.getTarefaById(idTarefa).toString();

    }

    public void associarPessoaTarefa(Pessoa pessoa, String idTarefa) {
        Tarefa tarefa = this.getTarefaById(idTarefa);
        tarefa.adicionarPessoaResponsavel(pessoa);
        pessoa.adicionarTarefa(tarefa);
    }

    public void removerPessoaTarefa(Pessoa pessoa, String idTarefa) {
        Tarefa tarefa = this.getTarefaById(idTarefa);
        tarefa.removerPessoaResponsavel(pessoa);
        pessoa.removerTarefa(tarefa);
    }


    public String cadastrarTarefaGerencial(Atividade atividade, String nome, String[] habilidades, String[] idTarefas) {
        int numeroNovaTarefa = this.calcularQuantidadeTarefas();
        String idNovaTarefa = atividade.getId() + "-" + numeroNovaTarefa;
        TarefaGerencial novaTarefa = new TarefaGerencial(idNovaTarefa, nome, 0, atividade);
        atividade.cadastrarTarefaGerencial(novaTarefa);
        boolean atividadeNaoTemTarefaGerencial = Objects.isNull(this.tarefasGerenciais.get(atividade));

        if (atividadeNaoTemTarefaGerencial) {
            this.tarefasGerenciais.put(atividade, new HashMap<>());
        }

        for (String id : idTarefas) {
            Tarefa t = this.getTarefaById(id);
            novaTarefa.addSubtarefa(t);
        }

        this.tarefasGerenciais.get(atividade).put(idNovaTarefa, novaTarefa);

        return idNovaTarefa;
    }

    public void adicionarNaTarefaGerencial(String idTarefaGerencial, String idTarefa) {
        Tarefa tarefa = this.getTarefaById(idTarefa);
        TarefaGerencial tarefaGerencial = this.getTarefaGerencialById(idTarefaGerencial);
        tarefaGerencial.addSubtarefa(tarefa);
    }

    public void removerDaTarefaGerencial(String idTarefaGerencial, String idTarefa) {
        Tarefa tarefa = this.getTarefaById(idTarefa);
        TarefaGerencial tarefaGerencial = this.getTarefaGerencialById(idTarefaGerencial);
        tarefaGerencial.removerTarefa(tarefa);
    }

    public int contarTodasTarefasNaTarefaGerencial(String idTarefaGerencial) {
        return this.getTarefaGerencialById(idTarefaGerencial).contarTarefas();
    }

    private int calcularQuantidadeTarefas() {
        int quantidadeTarefas = 0;
        for (Map<String, Tarefa> tarefasPorAtividade : this.tarefas.values()) {
            quantidadeTarefas += tarefasPorAtividade.size();
        }
        return quantidadeTarefas;
    }

    public String[] buscar(String nome) {
        List<String> ondeBuscar = new ArrayList<>();
        for (Map<String, Tarefa> mapTarefas : this.tarefas.values()) {
            for (Tarefa t : mapTarefas.values()) {
                ondeBuscar.add(t.getId() + "__" + t.getNome());
            }
        }
        for (Map<String, TarefaGerencial> mapGerenciais : this.tarefasGerenciais.values()) {
            for (TarefaGerencial t : mapGerenciais.values()) {
                ondeBuscar.add(t.getId() + "__" + t.getNome());
            }
        }
        Set<String> resultado = new HashSet<>();
        for (String nomeTarefa : ondeBuscar) {
            if (nomeTarefa.contains(nome)) {
                resultado.add(this.getTarefaById(nomeTarefa.split("__")[0]).toString());
            }
        }
        List<String> resultadoLista = new ArrayList<>(resultado);
        Collections.sort(resultadoLista, Collections.reverseOrder());
        return resultadoLista.toArray(new String[]{});
    }

    public String[] buscar(Atividade atividade, String nome) {
        List<String> ondeBuscar = new ArrayList<>();
        if (this.tarefas.get(atividade) != null) {
            for (Tarefa t : this.tarefas.get(atividade).values()) {
                ondeBuscar.add(t.getId() + "__" + t.getNome());
            }
        }
        if (this.tarefasGerenciais.get(atividade) != null) {
            for (TarefaGerencial t : this.tarefasGerenciais.get(atividade).values()) {
                ondeBuscar.add(t.getId() + "__" + t.getNome());
            }
        }
        Set<String> resultado = new HashSet<>();
        for (String nomeTarefa : ondeBuscar) {
            if (nomeTarefa.contains(nome)) {
                resultado.add(this.getTarefaById(nomeTarefa.split("__")[0]).toString());
            }
        }
        List<String> resultadoLista = new ArrayList<>(resultado);
        Collections.sort(resultadoLista, Collections.reverseOrder());
        return resultadoLista.toArray(new String[]{});
    }

    public String[] sugerir(Pessoa pessoa) {
        List<String> ondeBuscar = new ArrayList<>();
        for (Map<String, Tarefa> mapTarefas : this.tarefas.values()) {
            for (Tarefa t : mapTarefas.values()) {
                for (String habilidade : t.getHabilidades()) {
                    ondeBuscar.add(t.getId() + "__" + habilidade);
                }
            }
        }
        for (Map<String, TarefaGerencial> mapGerenciais : this.tarefasGerenciais.values()) {
            for (TarefaGerencial t : mapGerenciais.values()) {
                for (String habilidade : t.getHabilidades()) {
                    ondeBuscar.add(t.getId() + "__" + habilidade);
                }
            }
        }
        Set<String> resultado = new HashSet<>();
        for (String habilidade : pessoa.getHabilidades()) {
            for (String termo : ondeBuscar) {
                if (termo.contains(habilidade)) {
                    String id = termo.split("__")[0];
                    Tarefa tarefa = this.getTarefaById(id);
                    resultado.add(tarefa.toString());
                }
            }
        }
        return resultado.toArray(new String[]{});
    }
}
