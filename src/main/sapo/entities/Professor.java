package sapo.entities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Professor implements Funcao {

    private String siape;
    private String[] disciplinas;

    public Professor(String siape, String[] disciplinas) {
        this.siape = siape;
        this.disciplinas = disciplinas;
    }

    @Override
    public String gerarDetalhes() {
        return "Professor - " + this.siape + " - " + String.join(", ", this.disciplinas);
    }

    @Override
    public int incrementarNivel(Map<String, Tarefa> tarefas, Pessoa pessoa) {
        int nivelEmAndamento = 0;
        int nivelConcluida = 0;

        Map<String, Tarefa> tarefasEmAndamento = new HashMap<>();
        Map<String, Tarefa> tarefasConcluidas = new HashMap<>();

        for (Tarefa tarefa : tarefas.values()) {
            if (tarefa.getIsConcluida()) {
                tarefasConcluidas.put(tarefa.getId(), tarefa);
            } else {
                tarefasEmAndamento.put(tarefa.getId(), tarefa);
            }
        }
        nivelEmAndamento = this.calcularNivelEmAndamento(tarefasEmAndamento);
        nivelConcluida = this.calcularNivelConcluida(tarefasConcluidas, pessoa);


        return nivelEmAndamento + nivelConcluida;
    }

    private int calcularNivelEmAndamento(Map<String, Tarefa> tarefasEmAndamento) {
        return tarefasEmAndamento.size() / 4;
    }

    private int calcularNivelConcluida(Map<String, Tarefa> tarefasConcluidas, Pessoa pessoa) {
        int contHabilidadeEmComum = 0;

        for (Tarefa tarefaConcluida : tarefasConcluidas.values()) {
            String[] habilidadesTarefa = tarefaConcluida.getHabilidades();
            String[] habilidadesPessoa = pessoa.getHabilidades();
            if (this.contemHabilidadesEmComum(habilidadesTarefa, habilidadesPessoa)) {
                contHabilidadeEmComum += 1;
            }
        }

        return contHabilidadeEmComum;

    }

    private boolean contemHabilidadesEmComum(String[] habilidadesTarefa, String[] habilidadesPessoa) {

        for (int i = 0; i < habilidadesTarefa.length; i++) {
            boolean habilidadesPessoaContemHabilidadeTarefa = Arrays.asList(habilidadesPessoa).contains(habilidadesTarefa[i]);
            boolean disciplinasPessoaContemHabilidadeTarefa = Arrays.asList(this.disciplinas).contains(habilidadesTarefa[i]);
            if (!habilidadesPessoaContemHabilidadeTarefa && !disciplinasPessoaContemHabilidadeTarefa) {
                return false;
            }
        }
        return true;
    }


}
