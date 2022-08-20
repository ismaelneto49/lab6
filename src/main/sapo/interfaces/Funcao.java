package sapo.interfaces;

import sapo.entities.Pessoa;
import sapo.entities.Tarefa;

import java.util.Map;

public interface Funcao {

    String gerarDetalhes();

    int incrementarNivel(Map<String, Tarefa> tarefas, Pessoa pessoa);
}
