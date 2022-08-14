package sapo.entities;

import java.util.HashMap;
import java.util.Map;

public class Aluno implements Funcao {

    private String matricula;
    private int periodo;

    public Aluno(String matricula, int periodo) {
        this.matricula = matricula;
        this.periodo = periodo;
    }

    @Override
    public String gerarDetalhes() {
        return "Aluno - " + this.matricula + " - " + this.periodo + "\n";
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
        return tarefasEmAndamento.size() / 2;
    }

    private int calcularNivelConcluida(Map<String, Tarefa> tarefasConcluidas, Pessoa pessoa) {
        int contHabilidadeEmComum = 0;

        for (Tarefa tarefaConcluida : tarefasConcluidas.values()) {
            String[] habilidadesTarefa = tarefaConcluida.getHabilidades();
            String[] habilidadesPessoa = pessoa.getHabilidades();
            if (this.contemHabilidadeEmComum(habilidadesTarefa, habilidadesPessoa)) {
                contHabilidadeEmComum += 1;
            }
        }

        return (int) Math.ceil(1.5 * contHabilidadeEmComum) + tarefasConcluidas.size();

    }

    private boolean contemHabilidadeEmComum(String[] habilidadesTarefa, String[] habilidadesPessoa) {
        for (int i = 0; i < habilidadesTarefa.length; i++) {
            for (int j = i; j < habilidadesPessoa.length; j++) {
                if (habilidadesTarefa[i].equals(habilidadesPessoa[i])) {
                    return true;
                }
            }
        }
        return false;
    }

}
