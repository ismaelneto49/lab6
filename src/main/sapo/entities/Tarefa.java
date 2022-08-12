package sapo.entities;

import java.util.Set;

public class Tarefa {
    private String id;
    private String nome;
    private int duracao;
    private Atividade atividade;
    private boolean isConcluida;
    private String[] habilidades;
    private Set<Pessoa> pessoasResponsaveis;

        public Tarefa(String id, String nome, int duracao, Atividade atividade, String[] habilidades) {
            this.id = id;
            this.nome = nome;
            this.duracao = duracao;
            this.atividade = atividade;
            this.isConcluida = false;
            this.habilidades = habilidades;
        }

        public boolean getIsConcluida() {
            return this.isConcluida;
        }
        public void setNome(String novoNome) {
            this.nome = novoNome;
        }

        public void setHabilidades(String[] novasHabilidades) {
            this.habilidades = novasHabilidades;
        }

        public void concluirTarefa() {
            if (this.isConcluida) {
                throw new IllegalStateException("A tarefa já está concluída");
            }
            this.isConcluida = true;
        }

        public void adicionarPessoaResponsavel(Pessoa pessoa) {
            if (this.isConcluida) {
                throw new IllegalStateException("Não é possível adicionar pessoa responsável em uma tarefa já concluída");
            }
            this.pessoasResponsaveis.add(pessoa);
        }

        public void removerPessoaResponsavel(Pessoa pessoa) {
            if (this.isConcluida) {
                throw new IllegalStateException("Não é possível remover pessoa responsável em uma tarefa já concluída");
            }
            this.pessoasResponsaveis.remove(pessoa);
        }

        public void adicionarHoras(int horas) {
            if (this.isConcluida) {
                throw new IllegalStateException("Não é possível adicionar horas em uma tarefa já concluída");
            }
            this.duracao = this.duracao + horas;
        }

        public void removerHoras(int horas) {
            if (this.isConcluida) {
                throw new IllegalStateException("Não é possível remover horas em uma tarefa já concluída");
            }
            this.duracao = this.duracao - horas;
        }

        public String toStringReduzido() {
            return this.nome + " - " + this.id + "\n";
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(this.nome + " - " + this.id + "\n");
            builder.append("- " + this.atividade.getNome() + "\n");
            builder.append(String.join(", ", this.habilidades) + "\n");
            builder.append("===" + "\n");
            builder.append("Equipe" + "\n");
            // TODO: toStringDTO() of Pessoa
            for (Pessoa pessoa : this.pessoasResponsaveis) {
                builder.append(pessoa.toStringReduzido() + "\n");
            }

            return builder.toString();
        }

}