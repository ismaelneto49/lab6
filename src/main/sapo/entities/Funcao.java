package sapo.entities;

import java.util.Map;

public interface Funcao {
    public String gerarDetalhes();

    public int incrementarNivel(Map<String, Tarefa> tarefas, Pessoa pessoa);
}
